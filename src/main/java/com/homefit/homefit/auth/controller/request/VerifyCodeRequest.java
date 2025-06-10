package com.homefit.homefit.auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VerifyCodeRequest {
    @NotBlank(message = "username은 필수입니다")
    @Email(message = "username은 이메일 주소 양식이어야 합니다")
    private final String username;
    @NotBlank(message = "code는 필수입니다")
    private final String code;
    
    @Override
    public String toString() {
        return "VerifyRequest{username='" + username + "\', code='" + code + "\'}";
    }
}
