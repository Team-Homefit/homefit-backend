package com.homefit.homefit.consult.persistence;

import com.homefit.homefit.consult.domain.ConsultMessage;
import com.homefit.homefit.consult.domain.ConsultRoom;
import com.homefit.homefit.consult.persistence.po.ConsultMessagePo;
import com.homefit.homefit.consult.persistence.po.ConsultRoomPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ConsultRepository {
    int insertRoom(ConsultRoom room);

    int insertMessages(List<ConsultMessage> messages);

    String selectConversationByConsult(Long id);

    ConsultRoomPo selectRoomById(Long id);

    List<ConsultRoomPo> selectRoomsByMemberId(Long memberId);

    List<ConsultMessagePo> selectMessagesByRoomId(Long roomId);

    int deleteRoom(Long roomId, Boolean isDeleted);

    int updateRoomName(Long roomId, String name);
}
