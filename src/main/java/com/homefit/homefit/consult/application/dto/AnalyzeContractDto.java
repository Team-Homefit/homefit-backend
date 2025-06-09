package com.homefit.homefit.consult.application.dto;

import com.homefit.homefit.consult.domain.ConsultMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalyzeContractDto {
    private final Long consultRoomId;
    private final String conversationId;
    private final String answer;
    private final LocalDateTime answeredAt;

    public static AnalyzeContractDto from(String conversationId, ConsultMessage message) {
        return new AnalyzeContractDto(message.getConsultRoomId(), conversationId, message.getContent(), message.getCreatedAt());

    }
}
