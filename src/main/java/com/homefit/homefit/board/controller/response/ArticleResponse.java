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
                .map(comment -> Comment.createBlurredComment(comment.getCommentId()))
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
        
        public static Comment createBlurredComment(Long commentId) {
            String[] blurMessages = {
                "관심지역 설정 후 댓글을 확인할 수 있습니다. 이 지역의 소식을 놓치지 마세요!",
                "이 지역의 댓글을 보려면 관심지역을 설정하세요. 다양한 의견을 확인해보세요.",
                "댓글을 확인하려면 관심지역을 추가하세요. 지역 정보를 더 자세히 알아보세요.",
                "관심지역 설정으로 댓글을 확인하세요. 실시간 소식을 받아보세요.",
                "관심지역 설정 후 소통해보세요. 이웃들과 정보를 공유해보세요.",
                "이웃들과 소통해보세요.",
                "다양한 이야기를 들어보세요.",
                "지역 커뮤니티에 참여해보세요."
            };
            
            // commentId를 기반으로 랜덤하게 메시지 선택 (일관성 유지)
            int messageIndex = (int) (commentId % blurMessages.length);
            
            return new Comment(
                commentId,
                null,
                blurMessages[messageIndex],
                null,
                "익명",
                false,
                false
            );
        }
    }
} 