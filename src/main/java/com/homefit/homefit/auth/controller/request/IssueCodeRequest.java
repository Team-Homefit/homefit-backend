package com.homefit.homefit.auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IssueCodeRequest {
    @NotBlank(message = "username은 필수입니다")
    @Email(message = "username은 이메일 주소 양식이어야 합니다")
    private String username;
    @NotNull(message = "회원가입 요청 여부는 필수입니다")
    private Boolean isSignUp;

    @Override
    public String toString() {
        return "IssueCodeRequest{" +
                "username='" + username + '\'' +
                ", isSignUp=" + isSignUp +
                '}';
    }
}
