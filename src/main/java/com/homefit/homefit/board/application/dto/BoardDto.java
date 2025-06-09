package com.homefit.homefit.board.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardDto {
    private final Long id;
    private final String regionName;
    private final Long articleCount;
    private final Long interestCount;

    public static BoardDto of(Long id, String regionName, Long articleCount, Long interestCount) {
        return new BoardDto(id, regionName, articleCount, interestCount);
    }
}