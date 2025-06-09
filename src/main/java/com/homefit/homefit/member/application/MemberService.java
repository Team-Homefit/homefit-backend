package com.homefit.homefit.member.application;

import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.member.application.dto.MemberDto;
import com.homefit.homefit.member.controller.request.ModifyMemberRequest;
import com.homefit.homefit.member.controller.request.ModifyPasswordRequest;
import com.homefit.homefit.member.controller.request.ModifyRoleRequest;
import com.homefit.homefit.member.controller.request.SignUpRequest;
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
    public MemberDto signUp(SignUpRequest request, String username) {
        if (username == null) {
            throw new HomefitException(HttpStatus.FORBIDDEN, "이메일 인증 후 가입할 수 있습니다");
        }

        Member member = Member.of(
                username,
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                request.getGender(),
                request.getTel(),
                request.getBirthday(),
                LocalDateTime.now(),
                Role.BASIC
        );

        try {
            memberRepository.insert(member);
        } catch (DuplicateKeyException e) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다", username);
        }

        MemberPo memberPo = memberRepository.selectByUsername(username);

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
    public MemberDto modifyMember(ModifyMemberRequest request) {
        Long currentMemberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 로그인 한 사용자 정보를 조회할 수 없습니다"));
        
        MemberPo originMemberPo = memberRepository.selectById(currentMemberId);
        if (originMemberPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다");
        }
        
        Member member = originMemberPo.toDomain();
        member.updateNickname(request.getNickname());
        member.updateGender(request.getGender());
        member.updateTel(request.getTel());
        member.updateBirthday(request.getBirthday());
        
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
    public void modifyPassword(ModifyPasswordRequest request, String username) {
        if (username == null) {
            throw new HomefitException(HttpStatus.FORBIDDEN, "이메일 인증 후 변경할 수 있습니다");
        }

        MemberPo originMemberPo = memberRepository.selectByUsername(username);
        if (originMemberPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다");
        }

        Member member = originMemberPo.toDomain();
        if (passwordEncoder.matches(request.getNewPassword(), member.getEncodedPassword())) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "기존 비밀번호와 동일합니다");
        }
        
        member.updatePassword(passwordEncoder.encode(request.getNewPassword()));

        int result = memberRepository.updatePassword(member);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 정보 변경에 실패하였습니다");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public MemberDto modifyRole(ModifyRoleRequest request) {
        MemberPo originMemberPo = memberRepository.selectById(request.getId());
        if (originMemberPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다");
        }

        Member member = originMemberPo.toDomain();
        member.updateRole(request.getRole());

        int result = memberRepository.updateRole(member);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 권한 변경에 실패하였습니다");
        }
        
        MemberPo modifiedMemberPo = memberRepository.selectById(member.getId());
        if (modifiedMemberPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "사용자 저장 후 조회에 실패했습니다");
        }

        return MemberDto.from(modifiedMemberPo);
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
