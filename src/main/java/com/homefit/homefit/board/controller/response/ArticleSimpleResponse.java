package com.homefit.homefit.board.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleSimpleResponse {
    private final Long articleId;

    public static ArticleSimpleResponse of(Long id) {
    	return new ArticleSimpleResponse(id);
    }
}
