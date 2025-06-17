package com.homefit.homefit.consult.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KnowledgeQnACommand {
    private final Long consultRoomId;
    private final String message;
    private final Boolean isFirst;

    public static KnowledgeQnACommand of(Long consultRoomId, String message, Boolean isFirst) {
        return new KnowledgeQnACommand(consultRoomId, message, isFirst);
    }
}
