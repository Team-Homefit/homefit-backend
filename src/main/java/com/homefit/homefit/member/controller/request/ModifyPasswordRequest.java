package com.homefit.homefit.member.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyPasswordRequest {
    @NotBlank(message = "새 비밀번호는 필수입니다")
    @Pattern(
            regexp = "^[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{8,60}$",
            message = "비밀번호는 영문 대소문자, 숫자, 특수문자 조합의 8-60자여야 합니다"
    )
    private final String newPassword;

    @Override
    public String toString() {
        return "ModifyPasswordRequest{" +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
