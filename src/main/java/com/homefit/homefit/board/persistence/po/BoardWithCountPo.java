package com.homefit.homefit.board.persistence.po;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardWithCountPo {
    private final Long communityBoardId;
    private final String sggCd;
    private final LocalDateTime communityBoardCreatedAt;
    private final Long articleCount;
    private final Long interestCount;
}