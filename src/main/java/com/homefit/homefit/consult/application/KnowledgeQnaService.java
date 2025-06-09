package com.homefit.homefit.consult.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homefit.homefit.consult.application.dto.KnowledgeQnADto;
import com.homefit.homefit.consult.controller.request.KnowledgeQnARequest;
import com.homefit.homefit.consult.domain.ConsultMessage;
import com.homefit.homefit.consult.domain.ConsultRoom;
import com.homefit.homefit.consult.persistence.ConsultRepository;
import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.security.util.UserPrincipalUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KnowledgeQnaService {
    private final ChatClient chatClient;
    private final ConsultRepository consultRepository;

    public KnowledgeQnaService(
        @Qualifier("knowledgeChatClient") ChatClient chatClient,
        ConsultRepository consultRepository
    ) {
        this.chatClient = chatClient;
        this.consultRepository = consultRepository;
    }

    @Transactional
    @PreAuthorize(("hasRole('BASIC')"))
    public KnowledgeQnADto askKnowledge(KnowledgeQnARequest request) {
        Optional<Long> givenRoomId = getConsultRoomId(request);
        String conversationId = getConversationId(givenRoomId);

        // AI API 호출
        String response = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(request.getMessage()))
                .advisors(advSpec -> advSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
        log.info("AI 응답: {}", response);

        // AI 응답 저장
        Long roomId = givenRoomId.orElseGet(() -> saveRoom(conversationId, request.getMessage()));

        ConsultMessage memberMessage = ConsultMessage.of(roomId, request.getMessage(), true);
        ConsultMessage aiMessage = ConsultMessage.of(roomId, response, false);
        consultRepository.insertMessages(List.of(memberMessage, aiMessage));

        // AI 응답 반환
        return KnowledgeQnADto.from(conversationId, aiMessage);
    }

    private Optional<Long> getConsultRoomId(KnowledgeQnARequest request) {
        if (request.getConsultRoomId() == null) {
            if (!request.getIsFirst()) {
                throw new HomefitException(HttpStatus.BAD_REQUEST, "상담방 ID가 명확하지 않습니다.");
            }
            return Optional.empty();
        }

        if (request.getIsFirst()) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "상담방 ID가 명확하지 않습니다.");
        }
        return Optional.of(request.getConsultRoomId());
    }

    private String getConversationId(Optional<Long> consultId) {
        if (consultId.isEmpty()) {
            return UUID.randomUUID().toString();
        }

        String conversationId = consultRepository.selectConversationByConsult(consultId.get());

        if (conversationId == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "상담방을 찾을 수 없습니다");
        }

        return conversationId;
    }

    private Long saveRoom(String conversationId, String message) {
        Long memberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "인증 객체를 찾을 수 없습니다"));

        ConsultRoom room = ConsultRoom.of(memberId, conversationId, message.length() < 16 ? message : message.substring(0, 14) + "...");

        int result = consultRepository.insertRoom(room);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "상담방 생성에 실패했습니다");
        }

        return room.getId();
    }
}
