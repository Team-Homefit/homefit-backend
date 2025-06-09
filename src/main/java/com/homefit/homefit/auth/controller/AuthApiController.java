package com.homefit.homefit.auth.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Tag(name = "Authentication", description = "사용자 인증에 대한 API")
@RequestMapping("/auth")
@RestController
public class AuthApiController {
    @Operation(
    		summary = "로그인",
    		description = "이메일 인증 된 username과 password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증 성공", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "인증 실패", content = @Content())
            }
	)
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@ParameterObject @ModelAttribute SiginInRequest request) {
    	// 스웨거 명세를 위한 메소드로, 실제 인증은 SpringSecurity와 핸들러에서 이루어집니다.
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Operation(
    		summary = "로그아웃",
    		description = "사용자의 모든 세션이 만료됩니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그아웃 성공 (세션 만료)", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "인증 실패", content = @Content())
            }
	)
    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut() {
    	// 스웨거 명세를 위한 메소드로, 실제 로그아웃은 SpringSecurity와 핸들러에서 이루어집니다.
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
    
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class SiginInRequest {
    	private final String username;
    	private final String password;
    }
}
