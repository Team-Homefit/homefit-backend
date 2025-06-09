package com.homefit.homefit.board.application.dto;

import java.time.LocalDateTime;

import com.homefit.homefit.board.persistence.po.ArticleSimplePo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleSimpleDto {
    private final Long id;
    private final String title;
    private final String content;
    private final Long writerId;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastUpdatedAt;
    
    public static ArticleSimpleDto from(ArticleSimplePo po) {
    	return new ArticleSimpleDto(
    			po.getArticleId(),
    			po.getArticleTitle(),
    			po.getArticleContent(),
    			po.getArticleWriterId(),
    			po.getArticleCreatedAt(),
    			po.getArticleLastUpdatedAt());
    }
}
