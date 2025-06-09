package com.homefit.homefit.consult.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.consult.persistence.po.ConsultMessagePo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsultMessageDto {
    private final List<ConsultMessage> messages;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ConsultMessage {
        private final Long id;
        private final Long roomId;
        private final String content;
        private final Boolean isMemberMessage;
        private final LocalDateTime createdAt;

        public static ConsultMessage from(ConsultMessagePo message) {
            return new ConsultMessage(message.getConsultMessageId(), message.getConsultRoomId(), message.getConsultMessageContent(), message.getIsMemberMessage(), message.getConsultMessageCreatedAt());
        }
    }

    public static ConsultMessageDto from(List<ConsultMessagePo> messages) {
        return new ConsultMessageDto(messages.stream()
            .map(ConsultMessage::from)
            .collect(Collectors.toList()));
    }
}
