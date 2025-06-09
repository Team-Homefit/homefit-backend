package com.homefit.homefit.consult.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.consult.persistence.po.ConsultRoomPo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsultRoomsDto {
    private final List<ConsultRoom> rooms;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ConsultRoom {
        private final Long id;
        private final Long memberId;
        private final String conversationId;
        private final String name;
        private final LocalDateTime createdAt;

        public static ConsultRoom from(ConsultRoomPo room) {
            return new ConsultRoom(room.getConsultRoomId(), room.getMemberId(), room.getConversationId(), room.getConsultRoomName(), room.getConsultRoomCreatedAt());
        }
    }

    public static ConsultRoomsDto from(List<ConsultRoomPo> rooms) {
        return new ConsultRoomsDto(rooms.stream()
            .map(ConsultRoom::from)
            .collect(Collectors.toList()));
    }
}
