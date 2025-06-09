package com.homefit.homefit.board.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostArticleRequest {
    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 50, message = "제목은 50자를 초과할 수 없습니다")
    private final String title;

    @NotBlank(message = "내용은 필수입니다")
    @Size(max = 2000, message = "내용은 2000자를 초과할 수 없습니다")
    private final String content;
}
