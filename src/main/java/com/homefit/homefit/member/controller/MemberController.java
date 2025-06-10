package com.homefit.homefit.member.controller;

import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.member.application.MemberService;
import com.homefit.homefit.member.application.command.ModifyMemberCommand;
import com.homefit.homefit.member.application.command.ModifyPasswordCommand;
import com.homefit.homefit.member.application.command.ModifyRoleCommand;
import com.homefit.homefit.member.application.command.SignUpCommand;
import com.homefit.homefit.member.application.dto.MemberDto;
import com.homefit.homefit.member.controller.request.ModifyMemberRequest;
import com.homefit.homefit.member.controller.request.ModifyPasswordRequest;
import com.homefit.homefit.member.controller.request.ModifyRoleRequest;
import com.homefit.homefit.member.controller.request.SignUpRequest;
import com.homefit.homefit.member.controller.response.MemberResponse;
import com.homefit.homefit.member.controller.response.MemberRoleResponse;
import com.homefit.homefit.security.application.SessionService;

import com.homefit.homefit.security.util.UserPrincipalUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController implements MemberApiSpecification {
    private final MemberService memberService;
    private final SessionService sessionService;
    @Value("${session.attribute.username}")
    private static String attributeOfUsername;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponse> signUp(@RequestBody @Valid SignUpRequest request, HttpSession session) {
        log.info("회원가입 요청");

        String authenticatedUsername = (String) session.getAttribute(attributeOfUsername);
        SignUpCommand command = SignUpCommand.of(
                authenticatedUsername,
                request.getPassword(),
                request.getNickname(),
                request.getGender(),
                request.getTel(),
                request.getBirthday());

        MemberDto memberDto = memberService.signUp(command);

        session.setAttribute(attributeOfUsername, null);

        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponse.fromMember(memberDto));
    }

    @GetMapping("/{member-id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable("member-id") Long memberId) {
        log.info("사용자 조회 요청");

        MemberDto memberDto = memberService.searchMember(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(MemberResponse.fromMember(memberDto));
    }

    @PatchMapping
    public ResponseEntity<MemberResponse> modifyMember(@RequestBody @Valid ModifyMemberRequest request) {
        log.info("사용자 수정 요청");

        ModifyMemberCommand command = ModifyMemberCommand.of(
                request.getNickname(),
                request.getGender(),
                request.getTel(),
                request.getBirthday()
        );

        MemberDto memberDto = memberService.modifyMember(command);

        return ResponseEntity.status(HttpStatus.OK).body(MemberResponse.fromMember(memberDto));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> modifyPassword(@RequestBody @Valid ModifyPasswordRequest request, HttpSession session) {
        log.info("비밀번호 수정 요청");

        String authenticatedUsername = (String) session.getAttribute(attributeOfUsername);
        ModifyPasswordCommand command = ModifyPasswordCommand.of(authenticatedUsername, request.getNewPassword());

        memberService.modifyPassword(command);

        session.setAttribute(attributeOfUsername, null);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/role")
    public ResponseEntity<MemberRoleResponse> modifyRole(@RequestBody @Valid ModifyRoleRequest request) {
        log.info("권한 수정 요청");

        ModifyRoleCommand command = ModifyRoleCommand.of(request.getId(), request.getRole());

        memberService.modifyRole(command);
        sessionService.signOut(request.getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMe() {
        log.info("회원 탈퇴 요청");

        memberService.delete();
        Long currentMemberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 사용자의 ID를 찾을 수 없습니다"));
        sessionService.signOut(currentMemberId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
