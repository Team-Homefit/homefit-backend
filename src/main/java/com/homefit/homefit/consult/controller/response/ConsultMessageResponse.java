package com.homefit.homefit.consult.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.consult.application.dto.ConsultMessageDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsultMessageResponse {
    private final List<ConsultMessage> messages;

    public static ConsultMessageResponse from(ConsultMessageDto messages) {
        return new ConsultMessageResponse(messages.getMessages().stream()
            .map(ConsultMessage::from)
            .collect(Collectors.toList()));
    }
    
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class ConsultMessage {
        private final Long id;
        private final String content;
        private final Boolean isMemberMessage;
        private final LocalDateTime createdAt;

        public static ConsultMessage from(ConsultMessageDto.ConsultMessage message) {
            return new ConsultMessage(message.getId(), message.getContent(), message.getIsMemberMessage(), message.getCreatedAt());
        }
    }

}
