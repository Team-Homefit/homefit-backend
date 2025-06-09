package com.homefit.homefit.board.persistence.po;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardPo {
    private final Long communityBoardId;
    private final String sggCd;
    private final LocalDateTime communityBoardCreatedAt;

    public static BoardPo of(Long communityBoardId, String sggCd, LocalDateTime communityBoardCreatedAt) {
        return new BoardPo(communityBoardId, sggCd, communityBoardCreatedAt);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Article {
        private Long articleId;
        private String articleTitle;
        private Long viewCount;
        private Long likeCount;
        private Boolean isLiked;
        private Long commentCount;
        private String nickname;
        private LocalDateTime articleCreatedAt;
    }
}
