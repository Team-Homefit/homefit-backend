package com.homefit.homefit.board.persistence.po;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticlePagePo {
    private Long articleId;
    private Long communityBoardId;
    private String articleTitle;
    private LocalDateTime articleCreatedAt;
    private Long viewCount;
    private Long likeCount;
    private Long commentCount;
    private Boolean isLiked;
    private String nickname;
    private Long totalElements;
}
