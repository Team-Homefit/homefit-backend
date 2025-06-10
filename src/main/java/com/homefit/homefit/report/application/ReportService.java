package com.homefit.homefit.report.application;

import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.member.application.MemberService;
import com.homefit.homefit.member.application.command.ModifyRoleCommand;
import com.homefit.homefit.member.domain.Member;
import com.homefit.homefit.member.domain.Role;
import com.homefit.homefit.member.persistence.MemberRepository;
import com.homefit.homefit.member.persistence.po.MemberPo;
import com.homefit.homefit.report.application.command.ApologyCommand;
import com.homefit.homefit.report.application.command.BanCommand;
import com.homefit.homefit.report.application.command.ReportCommand;
import com.homefit.homefit.report.application.dto.BanDto;
import com.homefit.homefit.report.application.dto.ReportStatisticDto;
import com.homefit.homefit.report.domain.Ban;
import com.homefit.homefit.report.domain.BanDuration;
import com.homefit.homefit.report.domain.Report;
import com.homefit.homefit.report.persistence.BanRepository;
import com.homefit.homefit.report.persistence.ReportRepository;
import com.homefit.homefit.report.persistence.po.BanPo;
import com.homefit.homefit.report.persistence.po.ReportPo;
import com.homefit.homefit.report.persistence.po.ReportsPo;
import com.homefit.homefit.security.application.MemberDetailsService;
import com.homefit.homefit.security.application.dto.MemberDetails;
import com.homefit.homefit.security.util.UserPrincipalUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService {
	private final MemberDetailsService memberDetailsService;
    private final ReportRepository reportRepository;
    private final BanRepository banRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Transactional
    @PreAuthorize("hasRole('BASIC')")
    public void report(ReportCommand command) {
        Long reporterId = UserPrincipalUtil.getId()
            .orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 사용자를 찾을 수 없습니다"));

        Report report = Report.of(
                reporterId,
                command.getTargetSourceId(),
                command.getType()
        );

        int duplicatedReport = reportRepository.countDuplicateSinceYesterday(report);
        if (duplicatedReport != 0) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "하루 내에 신고한 내역이 있습니다");
        }

        int result = reportRepository.insert(report);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "신고 내역 저장에 실패했습니다");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ReportStatisticDto> searchReports() {
        List<ReportsPo> reportPos = reportRepository.selectAllYetDoneUntil();

        return reportPos.stream().map(ReportStatisticDto::from).toList();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void banMember(BanCommand command) {
        // 신고 무시
        if (command.getDuration().equals(BanDuration.ZERO)) {
            passReportAbout(command.getReporteeId(), command.getNow());
            return;
        }

        // 사용자 정지
        checkReportee(command.getReporteeId());

        Ban ban = Ban.of(
                command.getReporteeId(),
                command.getNow(),
                command.getNow().toLocalDate().plusDays(1).atStartOfDay().plusDays(command.getDuration().getDays()),
                command.getReason());

        int result = banRepository.insert(ban);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "정지 내역 저장에 실패했습니다");
        }

        processReportAbout(ban);

        memberService.modifyRole(ModifyRoleCommand.of(ban.getBannedMemberId(), Role.BANNED));
    }

    public BanDto getBan() {
        Long memberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 사용자를 찾을 수 없습니다"));

        BanPo banPo = banRepository.selectByMemberYetDoneUntil(memberId, LocalDateTime.now());
        if (banPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "정지 내역을 찾을 수 없습니다");
        }

        return BanDto.from(banPo);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('BASIC')")
    public void apology(ApologyCommand command) {
        Long memberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 사용자를 찾을 수 없습니다"));
        
        BanPo banPo = banRepository.selectById(command.getBanId());
        if (banPo == null) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "처리할 정지를 찾을 수 없습니다");
        }
        
        Ban ban = banPo.toDomain();
        
        // 반성문 제출
        ban.apology(memberId);
        int result = banRepository.updateSubmitApology(ban);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "정지 데이터 저장에 실패했습니다");
        }
        
        // 반성문 제출 시점이 정지 기간 이후면 역할 회복
        if (ban.getExpire().isBefore(command.getNow())) {
            recoverRole(ban.getBannedMemberId());
        }
    }
    
    private List<Report> getReports(Long reporteeId, LocalDateTime until) {
        List<ReportPo> reportPos = reportRepository.selectAllByReporteeUntilYetDone(reporteeId, until);
        
        return reportPos.stream()
                .map((reportPo) -> reportPo.toDomain())
                .toList();
    }

    private void passReportAbout(Long reporteeId, LocalDateTime until) {
        List<Report> reports = getReports(reporteeId, until);

        if (reports.isEmpty()) {
            return;
        }

        reports.forEach(report -> report.pass());
        reportRepository.updatePass(reports);
    }

    private void processReportAbout(Ban ban) {
        List<Report> reports = getReports(ban.getBannedMemberId(), ban.getStart());

        if (reports.isEmpty()) {
            return;
        }

        reports.forEach(report -> report.process(ban));
        reportRepository.updateBan(reports);
    }

    private void checkReportee(Long reporteeId) {
        MemberPo memberPo = memberRepository.selectById(reporteeId);

        if (memberPo == null) {
            log.error("존재하지 않는 사용자에 대한 정지 기능이 수행되었습니다.");
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 알 수 없는 오류가 발생하였습니다.");
        }

        if (memberPo.getRole().equals(Role.BANNED)) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "이미 정지된 사용자입니다");
        }
    }

    private void recoverRole(Long memberId) {
        // 역할 데이터 수정
        MemberPo memberPo = memberRepository.selectById(memberId);
        if (memberPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다");
        }

        Member member = memberPo.toDomain();
        member.updateRole(Role.BASIC);

        int result = memberRepository.updateRole(member);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 권한 변경에 실패하였습니다");
        }

        // SecurityContext의 인증 객체를 새 객체로 교체
        MemberDetails memberDetails = (MemberDetails) memberDetailsService.loadUserByUsername(member.getUsername());

        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
