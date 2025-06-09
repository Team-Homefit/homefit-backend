package com.homefit.homefit.auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyCodeRequest {
    @NotBlank(message = "username은 필수입니다")
    @Email(message = "username은 이메일 주소 양식이어야 합니다")
    private String username;
    @NotBlank(message = "code는 필수입니다")
    private String code;
    
    @Override
    public String toString() {
        return "VerifyRequest{username='" + username + "\', code='" + code + "\'}";
    }
}
