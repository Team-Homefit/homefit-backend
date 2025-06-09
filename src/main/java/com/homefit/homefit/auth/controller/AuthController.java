package com.homefit.homefit.auth.controller;

import com.homefit.homefit.auth.application.AuthService;
import com.homefit.homefit.auth.controller.request.IssueCodeRequest;
import com.homefit.homefit.auth.controller.request.VerifyCodeRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController implements AuthApiSpecification {
    @Value("${session.attribute.username}")
    private String attributeOfUsername;
    private final AuthService authService;

    @PostMapping("/code")
    public ResponseEntity<Void> issueCode(@RequestBody @Valid IssueCodeRequest request) {
        log.info("이메일 인증 코드 발급 요청: {}", request);

        authService.sendCodeMail(request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyCode(@RequestBody @Valid VerifyCodeRequest request, HttpSession session) {
        log.info("이메일 인증 코드 검사 요청: {}", request);

        authService.verifyCode(request);
        session.setAttribute(attributeOfUsername, request.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
