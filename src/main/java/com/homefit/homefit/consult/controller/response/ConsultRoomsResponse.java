package com.homefit.homefit.consult.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.consult.application.dto.ConsultRoomsDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsultRoomsResponse {
    private final List<ConsultRoom> rooms;

    public static ConsultRoomsResponse from(ConsultRoomsDto rooms) {
        return new ConsultRoomsResponse(rooms.getRooms().stream()
            .map(ConsultRoom::from)
            .collect(Collectors.toList()));
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ConsultRoom {
        private final Long id;
        private final String name;
        private final LocalDateTime createdAt;

        public static ConsultRoom from(ConsultRoomsDto.ConsultRoom room) {
            return new ConsultRoom(room.getId(), room.getName(), room.getCreatedAt());
        }
    }
}
