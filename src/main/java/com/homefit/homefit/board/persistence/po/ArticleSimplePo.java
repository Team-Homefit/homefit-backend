package com.homefit.homefit.board.persistence.po;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleSimplePo {
    private Long articleId;
    private String articleTitle;
    private String articleContent;
    private Long articleWriterId;
    private LocalDateTime articleCreatedAt;
    private LocalDateTime articleLastUpdatedAt;
}
