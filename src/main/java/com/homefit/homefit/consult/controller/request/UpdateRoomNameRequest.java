package com.homefit.homefit.consult.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRoomNameRequest {
    @NotNull(message = "채팅방 이름은 필수입니다")
    private String name;
}
