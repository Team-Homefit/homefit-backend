package com.homefit.homefit.board.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostArticleCommand {
    private final String title;
    private final String content;
    private final Long boardId;

    public static PostArticleCommand of(String title, String content, Long boardId) {
        return new PostArticleCommand(title, content, boardId);
    }
}
