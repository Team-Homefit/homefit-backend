package com.homefit.homefit.board.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.board.application.dto.ArticlePageDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticlePageResponse {
    private final List<Article> articles;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public static ArticlePageResponse from(ArticlePageDto dto) {
        List<Article> articles = dto.getArticles().stream()
                .map(Article::from)
                .collect(Collectors.toList());
        return new ArticlePageResponse(articles, dto.getPage(), dto.getSize(), dto.getTotalElements(), dto.getTotalPages());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Article {
        private final Long id;
        private final String title;
        private final Long boardId;
        private final Long viewCount;
        private final Long likeCount;
        private final Long commentCount;
        private final Boolean isLiked;
        private final String nickname;
        private final LocalDateTime createdAt;

        public static Article from(ArticlePageDto.Article dto) {
            return new Article(dto.getId(), dto.getTitle(), dto.getBoardId(), dto.getViewCount(),
                    dto.getLikeCount(), dto.getCommentCount(), dto.getIsLiked(), dto.getNickname(), dto.getCreatedAt());
        }

    }
}