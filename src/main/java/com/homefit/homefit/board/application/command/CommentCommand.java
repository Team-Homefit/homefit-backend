package com.homefit.homefit.board.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCommand {
    private final Long articleId;
    private final Long parentCommentId;
    private final String content;

    public static CommentCommand of(Long articleId, Long parentCommentId, String content) {
        return new CommentCommand(articleId, parentCommentId, content);
    }
}
