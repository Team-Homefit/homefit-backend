package com.homefit.homefit.consult.controller.response;

import com.homefit.homefit.consult.application.dto.AnalyzeContractDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalyzeContractResponse {
    private final Long consultRoomId;
    private final String conversationId;
    private final String answer;
    private final LocalDateTime answeredAt;

    public static AnalyzeContractResponse from(AnalyzeContractDto dto) {
        return new AnalyzeContractResponse(
                dto.getConsultRoomId(),
                dto.getConversationId(),
                dto.getAnswer(),
                dto.getAnsweredAt()
        );
    }
}
