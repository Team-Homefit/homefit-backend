package com.homefit.homefit.auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IssueCodeRequest {
    @NotBlank(message = "username은 필수입니다")
    @Email(message = "username은 이메일 주소 양식이어야 합니다")
    private final String username;
    @NotNull(message = "회원가입 요청 여부는 필수입니다")
    private final Boolean isSignUp;

    @Override
    public String toString() {
        return "IssueCodeRequest{" +
                "username='" + username + '\'' +
                ", isSignUp=" + isSignUp +
                '}';
    }
}
