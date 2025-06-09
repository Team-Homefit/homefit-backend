package com.homefit.homefit.notification.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateNotificationRequest {
    @NotBlank(message = "제목은 필수입니다")
    @Size(min = 3, max = 30, message = "제목은 3-30자 사이여야 합니다")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]+$", message = "제목은 한글, 영문, 숫자만 사용 가능합니다")
    private final String title;

    @NotBlank(message = "내용은 필수입니다")
    @Size(min = 10, max = 1000, message = "내용은 10-1000자 사이여야 합니다")
    private final String content;
}