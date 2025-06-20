package com.homefit.homefit.consult.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRoomNameCommand {
    private final Long roomId;
    private final String name;

    public static UpdateRoomNameCommand of(Long roomId, String name) {
        return new  UpdateRoomNameCommand(roomId, name);
    }
}
