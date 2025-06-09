package com.homefit.homefit.consult.persistence.po;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsultRoomPo {
    private final Long consultRoomId;
    private final Long memberId;
    private final String conversationId;
    private final String consultRoomName;
    private final LocalDateTime consultRoomCreatedAt;
    private final Boolean consultRoomIsDeleted;
}
