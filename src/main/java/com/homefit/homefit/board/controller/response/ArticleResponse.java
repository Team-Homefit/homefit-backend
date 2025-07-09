package com.homefit.homefit.board.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.board.application.dto.ArticleDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleResponse {
    private final Long articleId;
    private final String regionName;
    private final String articleTitle;
    private final String articleContent;
    private final LocalDateTime articleCreatedAt;
    private final LocalDateTime articleLastUpdatedAt;
    private final Integer viewCount;
    private final Integer likeCount;
    private final Boolean isLiked;
    private final String nickname;
    private final Long writerId;
    private final Boolean isInterestedRegion;
    private final List<Comment> comments;

    public static ArticleResponse from(ArticleDto articleDto) {
        ArticleResponseBuilder builder = ArticleResponse.builder()
            .articleId(articleDto.getId())
            .regionName(articleDto.getRegionName())
            .articleTitle(articleDto.getTitle())
            .articleContent(articleDto.getContent())
            .articleCreatedAt(articleDto.getCreatedAt())
            .articleLastUpdatedAt(articleDto.getLastUpdatedAt())
            .viewCount(articleDto.getViewCount().intValue())
            .likeCount(articleDto.getLikeCount().intValue())
            .isLiked(articleDto.getIsLiked())
            .nickname(articleDto.getNickname())
            .writerId(articleDto.getWriterId())
            .isInterestedRegion(articleDto.getIsInterestedRegion());
        
        // 관심지역인 경우 실제 댓글, 아닌 경우 블러용 더미 댓글
        if (articleDto.getIsInterestedRegion()) {
            builder.comments(articleDto.getComments().stream()
                .map(Comment::from)
                .collect(Collectors.toList()));
        } else {
            // 블러 처리용 더미 댓글 생성
            List<Comment> dummyComments = articleDto.getComments().stream()
                .map(comment -> Comment.createBlurredComment())
                .collect(Collectors.toList());
            builder.comments(dummyComments);
        }
        
        return builder.build();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Comment {
        private final Long commentId;
        private final Long commentWriterId;
        private final String commentContent;
        private final LocalDateTime commentCreatedAt;
        private final String nickname;
        private final Boolean isReply;
        private final Boolean isDeleted;

        public static Comment from(ArticleDto.Comment commentDto) {
            return new Comment(
                commentDto.getCommentId(),
                commentDto.getCommentWriterId(),
                commentDto.getCommentContent(),
                commentDto.getCommentCreatedAt(),
                commentDto.getNickname(),
                commentDto.getIsReply(),
                commentDto.getIsDeleted()
            );
        }
        
        public static Comment createBlankComment() {
            return new Comment(null, null, null, null, null, false, false);
        }
    }
} 