package com.homefit.homefit.board.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentRequest {
	@NotNull(message = "게시글 ID는 필수입니다")
	private final Long articleId;
	private final Long parentCommentId;
	@NotBlank(message = "댓글 내용은 필수입니다")
	private final String content;
}
