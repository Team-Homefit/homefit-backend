package com.homefit.homefit.board.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.board.persistence.po.ArticlePo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String regionName;
    private final Long writerId;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastUpdatedAt;
    private final Long viewCount;
    private final Long likeCount;
    private final Boolean isLiked;
    private final Boolean isInterestedRegion;
    private final List<Comment> comments;

    public static ArticleDto from(ArticlePo po) {
        List<Comment> comments = po.getComments().stream()
                .map(Comment::from)
                .collect(Collectors.toList());

        return new ArticleDto(
                po.getArticleId(),
                po.getArticleTitle(),
                po.getArticleContent(),
                po.getRegionName(),
                po.getArticleWriterId(),
                po.getNickname(),
                po.getArticleCreatedAt(),
                po.getArticleLastUpdatedAt(),
                po.getViewCount(),
                po.getLikeCount(),
                po.getIsLiked(),
                po.getIsInterestedRegion(),
                comments);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Comment {
        private final Long commentId;
        private final Long commentWriterId;
        private final Long parentCommentId;
        private final String commentContent;
        private final LocalDateTime commentCreatedAt;
        private final Boolean commentIsDeleted;
        private final String nickname;
        private final Boolean isReply;
        private final Boolean isDeleted;

        public static Comment from(ArticlePo.Comment po) {
            return new Comment(
                    po.getCommentId(),
                    po.getCommentWriterId(),
                    po.getParentCommentId(),
                    po.getCommentContent(),
                    po.getCommentCreatedAt(),
                    po.getCommentIsDeleted(),
                    po.getNickname(),
                    po.getIsReply(),
                    po.getCommentIsDeleted());
        }
    }
}
