package com.homefit.homefit.board.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {
    private Long id;
    private String sggCd;
    private LocalDateTime createdAt;
    private String sggName;
    private String sidoCd;
    private String sidoName;
    private List<Article> articles;

    public static Board of(Long id, String sggCd, LocalDateTime createdAt, String sggName,
            String sidoCd, String sidoName, List<Article> articles) {
        return new Board(id, sggCd, createdAt, sggName, sidoCd, sidoName, articles);
    }

    // 게시글 목록 조회를 위한 inner class
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Article {
        private Long id;
        private String title;
        private Long viewCount;
        private Long likeCount;
        private Boolean isLiked;
        private Long commentCount;
        private String nickname;
        private LocalDateTime createdAt;
        private String sggCd;
        private String sggName;
        private String sidoCd;
        private String sidoName;
    }
}