package com.homefit.homefit.board.persistence.po;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
public class ArticlePo {
    private Long articleId;
    private Long articleWriterId;
    private Long communityBoardId;
    private String regionName;
    private String articleTitle;
    private String articleContent;
    private LocalDateTime articleCreatedAt;
    private LocalDateTime articleLastUpdatedAt;
    private Boolean articleIsDeleted;
    private Boolean isLiked;
    private Long likeCount;
    private Long commentCount;
    private Long viewCount;
    private String nickname;
    private List<Comment> comments;
    private String sggCd;
    private String sggName;
    private String sidoCd;
    private String sidoName;
    private Boolean isInterestedRegion;
    private Boolean isDeleted;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Comment {
        private Long commentId;
        private Long commentWriterId;
        private Long parentCommentId;
        private String commentContent;
        private LocalDateTime commentCreatedAt;
        private Boolean commentIsDeleted;
        private String nickname;
        private Boolean isReply;
    }
}
