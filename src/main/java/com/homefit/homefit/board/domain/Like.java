package com.homefit.homefit.board.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Like {
	private Long articleId;
	private Long memberId;
	private LocalDateTime createdAt;
	
	public static Like of(Long articleId, Long memberId) {
		return new Like(articleId, memberId, LocalDateTime.now());
	}
}
