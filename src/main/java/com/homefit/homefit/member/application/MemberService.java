package com.homefit.homefit.member.application;

import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.member.application.command.ModifyMemberCommand;
import com.homefit.homefit.member.application.command.ModifyPasswordCommand;
import com.homefit.homefit.member.application.command.ModifyRoleCommand;
import com.homefit.homefit.member.application.command.SignUpCommand;
import com.homefit.homefit.member.application.dto.MemberDto;
import com.homefit.homefit.member.domain.Member;
import com.homefit.homefit.member.domain.Role;
import com.homefit.homefit.member.persistence.MemberRepository;
import com.homefit.homefit.member.persistence.po.MemberPo;
import com.homefit.homefit.security.util.UserPrincipalUtil;

import java.time.LocalDateTime;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberDto signUp(SignUpCommand command) {
        Member member = Member.of(
                command.getUsername(),
                passwordEncoder.encode(command.getPassword()),
                command.getNickname(),
                command.getGender(),
                command.getTel(),
                command.getBirthday(),
                LocalDateTime.now(),
                Role.BASIC
        );

        try {
            memberRepository.insert(member);
        } catch (DuplicateKeyException e) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다", member.getUsername());
        }

        MemberPo memberPo = memberRepository.selectByUsername(member.getUsername());

        return MemberDto.from(memberPo);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'BASIC')")
    public MemberDto searchMember(Long id) {
        Long currentMemberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 로그인 한 사용자 정보를 조회할 수 없습니다"));
        
        MemberPo memberPo = memberRepository.selectById(id);
        if (memberPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다");
        }

        if (memberPo.getMemberId() == currentMemberId) {
            return MemberDto.from(memberPo);
        }
        return MemberDto.fromWithMask(memberPo);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'BASIC')")
    public MemberDto modifyMember(ModifyMemberCommand command) {
        Long currentMemberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 로그인 한 사용자 정보를 조회할 수 없습니다"));
        
        MemberPo originMemberPo = memberRepository.selectById(currentMemberId);
        if (originMemberPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다");
        }
        
        Member member = originMemberPo.toDomain();
        member.updateNickname(command.getNickname());
        member.updateGender(command.getGender());
        member.updateTel(command.getTel());
        member.updateBirthday(command.getBirthday());
        
        int result = memberRepository.updateMember(member);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 정보 변경에 실패하였습니다");
        }
        
        MemberPo modifiedMemberPo = memberRepository.selectById(member.getId());
        if (modifiedMemberPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "사용자 저장 후 조회에 실패했습니다");
        }

        return MemberDto.from(modifiedMemberPo);
    }

    @Transactional
    public void modifyPassword(ModifyPasswordCommand command) {

        MemberPo originMemberPo = memberRepository.selectByUsername(command.getUsername());
        if (originMemberPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다");
        }

        Member member = originMemberPo.toDomain();
        if (passwordEncoder.matches(command.getNewPassword(), member.getEncodedPassword())) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "기존 비밀번호와 동일합니다");
        }
        
        member.updatePassword(passwordEncoder.encode(command.getNewPassword()));

        int result = memberRepository.updatePassword(member);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 정보 변경에 실패하였습니다");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void modifyRole(ModifyRoleCommand command) {
        MemberPo originMemberPo = memberRepository.selectById(command.getId());
        if (originMemberPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다");
        }

        Member member = originMemberPo.toDomain();
        member.updateRole(command.getRole());

        int result = memberRepository.updateRole(member);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 권한 변경에 실패하였습니다");
        }
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'BASIC')")
    public void delete() {
        Long currentMemberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 로그인 한 사용자 정보를 조회할 수 없습니다"));

        MemberPo memberPo = memberRepository.selectById(currentMemberId);
        if (memberPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다");
        }

        Member member = memberPo.toDomain();
        member.delete();

        int result = memberRepository.updateDeletion(member);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 삭제에 실패하였습니다");
        }
    }
}
