package com.homefit.homefit.consult.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsultRoom {
    private Long id;
    private Long memberId;
    private String conversationId;
    private String name;
    private LocalDateTime createdAt;
    private Boolean isDeleted;

    public static ConsultRoom of(Long memberId, String conversationId, String name) {
        return new ConsultRoom(null, memberId, conversationId, name, LocalDateTime.now(), false);
    }

    public static ConsultRoom of(Long id, Long memberId, String conversationId, String name, LocalDateTime createdAt, Boolean isDeleted) {
        return new ConsultRoom(id, memberId, conversationId, name, createdAt, isDeleted);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
