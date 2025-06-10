package com.homefit.homefit.board.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyArticleCommand {
    private final Long id;
    private final String title;
    private final String content;
    private final Long boardId;

    public static ModifyArticleCommand of(Long id, String title, String content, Long boardId) {
        return new ModifyArticleCommand(id, title, content, boardId);
    }
}
