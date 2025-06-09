package com.homefit.homefit.board.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.board.persistence.po.ArticlePagePo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticlePageDto {
    private final List<Article> articles;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public static ArticlePageDto of(List<ArticlePagePo> pos, int page, int size) {
        long totalElements = pos.isEmpty() ? 0 : pos.get(0).getTotalElements();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        List<Article> articles = pos.stream()
                .map(Article::from)
                .collect(Collectors.toList());
                
        return new ArticlePageDto(articles, page, size, totalElements, totalPages);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Article {
        private final Long id;
        private final String title;
        private final Long boardId;
        private final String nickname;
        private final LocalDateTime createdAt;
        private final Long viewCount;
        private final Long likeCount;
        private final Long commentCount;
        private final Boolean isLiked;

        public static Article from(ArticlePagePo po) {
            return new Article(
                po.getArticleId(),
                po.getArticleTitle(),
                po.getCommunityBoardId(),
                po.getNickname(),
                po.getArticleCreatedAt(),
                po.getViewCount(),
                po.getLikeCount(),
                po.getCommentCount(),
                po.getIsLiked()
            );
        }
    }
}
