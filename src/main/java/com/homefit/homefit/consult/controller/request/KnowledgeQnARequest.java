package com.homefit.homefit.consult.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KnowledgeQnARequest {
    private final Long consultRoomId;
    @NotNull(message = "질문은 필수입니다")
    private final String message;
    @NotNull(message = "첫 메시지 플래그는 필수입니다")
    private final Boolean isFirst;
}
