package com.homefit.homefit.report.application;

import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.member.application.MemberService;
import com.homefit.homefit.member.application.command.ModifyRoleCommand;
import com.homefit.homefit.member.controller.request.ModifyRoleRequest;
import com.homefit.homefit.member.domain.Member;
import com.homefit.homefit.member.domain.Role;
import com.homefit.homefit.member.persistence.MemberRepository;
import com.homefit.homefit.member.persistence.po.MemberPo;
import com.homefit.homefit.report.application.dto.BanDto;
import com.homefit.homefit.report.application.dto.ReportStatisticDto;
import com.homefit.homefit.report.controller.request.ApologyRequest;
import com.homefit.homefit.report.controller.request.BanRequest;
import com.homefit.homefit.report.controller.request.ReportRequest;
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
    public void report(ReportRequest request) {
        Long reporterId = UserPrincipalUtil.getId()
            .orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 사용자를 찾을 수 없습니다"));

        Report report = Report.of(
                reporterId,
                request.getTargetSourceId(),
                request.getType()
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
    public void banMember(BanRequest request) {
        LocalDateTime now = LocalDateTime.now();
        
        // 신고 무시 요청
        if (request.getDuration().equals(BanDuration.ZERO)) {
            List<Report> reports = getReports(request.getReporteeId(), now);
            if (reports.isEmpty()) {
                return;
            }

			reports.forEach(report -> report.pass());
			reportRepository.updatePass(reports);
            return;
        }
        
    	// 사용자 정지 로직
        MemberPo memberPo = memberRepository.selectById(request.getReporteeId());
        if (memberPo.getRole().equals(Role.BANNED)) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "이미 정지된 사용자입니다");
        }

        Ban ban = Ban.of(request.getReporteeId(), now, now.toLocalDate().plusDays(1).atStartOfDay().plusDays(request.getDuration().getDays()), request.getReason());

        int result = banRepository.insert(ban);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "정지 내역 저장에 실패했습니다");
        }

        List<Report> reports = getReports(request.getReporteeId(), now);
		if (!reports.isEmpty()) {
			reports.forEach(report -> report.process(ban));
			reportRepository.updateBan(reports);
		}
        
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
    public void apology(ApologyRequest request) {
        Long memberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 사용자를 찾을 수 없습니다"));
        LocalDateTime now = LocalDateTime.now();
        log.info("member: " + memberId);
        
        BanPo banPo = banRepository.selectById(request.getBanId());
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
        
        // 반성문 제출 시점이 정지 기간 이후면 권한 회복
        if (ban.getExpire().isBefore(now)) {
            MemberPo originMemberPo = memberRepository.selectById(memberId);
            if (originMemberPo == null) {
                throw new HomefitException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다");
            }

            Member member = originMemberPo.toDomain();
            member.updateRole(Role.BASIC);

            result = memberRepository.updateRole(member);
            if (result == 0) {
                throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 권한 변경에 실패하였습니다");
            }
            
            // SecurityContext의 정보 회복
            MemberDetails updatedUserDetails = (MemberDetails) memberDetailsService.loadUserByUsername(member.getUsername());

            // 2. 새 인증 객체 생성 (비밀번호는 null, 인증 완료 상태 true)
            UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(
                    updatedUserDetails,
                    null,
                    updatedUserDetails.getAuthorities()
                );

            // 3. SecurityContext에 새 인증 객체 설정
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }
    
    private List<Report> getReports(Long reporteeId, LocalDateTime until) {
        List<ReportPo> reportPos = reportRepository.selectAllByReporteeUntilYetDone(reporteeId, until);
        
        return reportPos.stream()
                .map((reportPo) -> reportPo.toDomain())
                .toList();
    }
}
