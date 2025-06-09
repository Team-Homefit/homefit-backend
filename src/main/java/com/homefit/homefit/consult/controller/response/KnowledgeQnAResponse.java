package com.homefit.homefit.consult.controller.response;

import java.time.LocalDateTime;

import com.homefit.homefit.consult.application.dto.KnowledgeQnADto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KnowledgeQnAResponse {
    private final Long consultRoomId;
    private final String conversationId;
    private final String answer;
    private final LocalDateTime answeredAt;

    public static KnowledgeQnAResponse from(KnowledgeQnADto dto) {
        return new KnowledgeQnAResponse(
                dto.getConsultRoomId(),
                dto.getConversationId(),
                dto.getAnswer(),
                dto.getAnsweredAt());
    }
}
