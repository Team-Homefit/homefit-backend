package com.homefit.homefit.consult.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homefit.homefit.consult.persistence.ConsultRepository;
import com.homefit.homefit.consult.application.dto.ConsultMessageDto;
import com.homefit.homefit.consult.application.dto.ConsultRoomsDto;
import com.homefit.homefit.consult.domain.ConsultRoom;
import com.homefit.homefit.consult.persistence.po.ConsultMessagePo;
import com.homefit.homefit.consult.persistence.po.ConsultRoomPo;
import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.security.util.UserPrincipalUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultService {
    private final ConsultRepository consultRepository;

    @PreAuthorize("hasAnyRole('BASIC')")
    public ConsultRoomsDto getRooms() {
        Long memberId = UserPrincipalUtil.getId().orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED,
                "로그인 후 이용해주세요"));

        List<ConsultRoomPo> rooms = consultRepository.selectRoomsByMemberId(memberId);
        return ConsultRoomsDto.from(rooms);
    }

    @PreAuthorize("hasAnyRole('BASIC')")
    public ConsultMessageDto getMessages(Long roomId) {
        List<ConsultMessagePo> messages = consultRepository.selectMessagesByRoomId(roomId);
        return ConsultMessageDto.from(messages);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('BASIC')")
    public void updateRoomName(Long roomId, String name) {
        ConsultRoomPo roomPo = consultRepository.selectRoomById(roomId);

        if (roomPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다");
        }

        ConsultRoom room = ConsultRoom.of(roomPo.getConsultRoomId(), roomPo.getMemberId(), roomPo.getConversationId(), roomPo.getConsultRoomName(), roomPo.getConsultRoomCreatedAt(), roomPo.getConsultRoomIsDeleted());
        room.updateName(name);

        int result = consultRepository.updateRoomName(room.getId(), room.getName());
        if (result == 0) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "채팅방 이름 수정에 실패했습니다");
        }
    }

    @Transactional
    @PreAuthorize("hasAnyRole('BASIC')")
    public void deleteRoom(Long roomId) {
        ConsultRoomPo roomPo = consultRepository.selectRoomById(roomId);

        if (roomPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다");
        }

        ConsultRoom room = ConsultRoom.of(roomPo.getConsultRoomId(), roomPo.getMemberId(), roomPo.getConversationId(), roomPo.getConsultRoomName(), roomPo.getConsultRoomCreatedAt(), roomPo.getConsultRoomIsDeleted());
        room.delete();

        int result = consultRepository.deleteRoom(room.getId(), room.getIsDeleted());
        if (result == 0) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "채팅방 삭제에 실패했습니다");
        }
    }
}