package com.homefit.homefit.consult.persistence.po;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsultMessagePo {
    private final Long consultMessageId;
    private final Long consultRoomId;
    private final String consultMessageContent;
    private final Boolean isMemberMessage;
    private final LocalDateTime consultMessageCreatedAt;
}
