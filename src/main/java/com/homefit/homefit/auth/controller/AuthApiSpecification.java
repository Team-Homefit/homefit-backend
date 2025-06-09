package com.homefit.homefit.auth.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import com.homefit.homefit.auth.controller.request.IssueCodeRequest;
import com.homefit.homefit.auth.controller.request.VerifyCodeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "사용자 인증에 대한 API")
public interface AuthApiSpecification {

    @Operation(
        summary = "인증 코드 발급",
        description = "주어진 username(이메일)에 인증 코드를 발급합니다.",
        requestBody = @RequestBody(
                required = true,
                description = "코드를 발급할 username",
                content = @Content(mediaType = "application/json")
        ),
        responses = {
                @ApiResponse(responseCode = "200", description = "발급 성공", content = @Content()),
                @ApiResponse(responseCode = "400", description = "올바르지 않거나 존재하지 않는 이메일", content = @Content())
        }
    )
    ResponseEntity<Void> issueCode(IssueCodeRequest request);

    @Operation(
        summary = "인증 코드 검증",
        description = "주어진 username(이메일)과 코드로 발급한 코드와 일치하는지 확인합니다.",
        requestBody = @RequestBody(
                required = true,
                description = """
                        인증 정보<br>
                        이메일과 코드는 필수입니다.
                        """,
                        content = @Content(mediaType = "application/json")
        ),
        responses = {
                @ApiResponse(responseCode = "200", description = "인증 성공", content = @Content()),
                @ApiResponse(responseCode = "400", description = "인증 실패", content = @Content())
        }
    )
    ResponseEntity<Void> verifyCode(VerifyCodeRequest request, HttpSession session);
}
