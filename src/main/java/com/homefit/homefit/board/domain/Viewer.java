package com.homefit.homefit.board.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Viewer {
    private Long articleId;
    private Long memberId;
    private LocalDateTime viewTime;

    public static Viewer of(Long articleId, Long memberId) {
        return new Viewer(articleId, memberId, LocalDateTime.now());
    }
}