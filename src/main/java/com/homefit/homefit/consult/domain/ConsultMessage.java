package com.homefit.homefit.consult.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsultMessage {
    private Long id;
    private Long consultRoomId;
    private String content;
    private Boolean isMemberMessage;
    private LocalDateTime createdAt;

    public static ConsultMessage of(Long consultRoomId, String content, Boolean isMemberMessage) {
        return new ConsultMessage(null, consultRoomId, content, isMemberMessage, LocalDateTime.now());
    }
}
