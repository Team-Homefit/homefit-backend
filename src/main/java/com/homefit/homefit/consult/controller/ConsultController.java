package com.homefit.homefit.consult.controller;

import com.homefit.homefit.consult.application.command.AnalyzeContractCommand;
import com.homefit.homefit.consult.application.command.KnowledgeQnACommand;
import com.homefit.homefit.consult.application.command.UpdateRoomNameCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homefit.homefit.consult.application.AnalyzeConsultService;
import com.homefit.homefit.consult.application.ConsultService;
import com.homefit.homefit.consult.application.KnowledgeQnaService;
import com.homefit.homefit.consult.application.dto.AnalyzeContractDto;
import com.homefit.homefit.consult.application.dto.ConsultMessageDto;
import com.homefit.homefit.consult.application.dto.ConsultRoomsDto;
import com.homefit.homefit.consult.application.dto.KnowledgeQnADto;
import com.homefit.homefit.consult.controller.request.AnalyzeContractRequest;
import com.homefit.homefit.consult.controller.request.KnowledgeQnARequest;
import com.homefit.homefit.consult.controller.request.UpdateRoomNameRequest;
import com.homefit.homefit.consult.controller.response.AnalyzeContractResponse;
import com.homefit.homefit.consult.controller.response.ConsultMessageResponse;
import com.homefit.homefit.consult.controller.response.ConsultRoomsResponse;
import com.homefit.homefit.consult.controller.response.KnowledgeQnAResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/consult")
@RestController
public class ConsultController implements ConsultApiSpecification {
    private final AnalyzeConsultService analyzeConsultService;
    private final KnowledgeQnaService knowledgeQnaService;
    private final ConsultService consultService;

    @PostMapping(path = "/analyze")
    public ResponseEntity<AnalyzeContractResponse> analyzeContract(@ModelAttribute AnalyzeContractRequest request) {
        log.info("계약서 분석 요청: {}", request);

        AnalyzeContractCommand command = AnalyzeContractCommand.of(
                request.getContractFile(),
                request.getConsultRoomId(),
                request.getMessage(),
                request.getIsFirst()
        );

        AnalyzeContractDto dto = analyzeConsultService.analyzeContract(command);

        return ResponseEntity.ok(AnalyzeContractResponse.from(dto));
    }

    @PostMapping(path = "/knowledge")
    public ResponseEntity<KnowledgeQnAResponse> askKnowledge(@RequestBody @Valid KnowledgeQnARequest request) {
        log.info("부동산 질문 요청: {}", request);
        KnowledgeQnACommand commnad = KnowledgeQnACommand.of(request.getConsultRoomId(), request.getMessage(), request.getIsFirst());

        KnowledgeQnADto dto = knowledgeQnaService.askKnowledge(commnad);

        return ResponseEntity.ok(KnowledgeQnAResponse.from(dto));
    }

    @GetMapping(path = "/rooms")
    public ResponseEntity<ConsultRoomsResponse> getRooms() {
        log.info("챗봇 대화방 목록 조회 요청");
        ConsultRoomsDto consultRoomDtos = consultService.getRooms();
        return ResponseEntity.ok(ConsultRoomsResponse.from(consultRoomDtos));
    }

    @GetMapping(path = "/rooms/{roomId}/messages")
    public ResponseEntity<ConsultMessageResponse> getMessages(@PathVariable Long roomId) {
        log.info("챗봇 대화 조회 요청");   
        ConsultMessageDto consultMessageDtos = consultService.getMessages(roomId);
        return ResponseEntity.ok(ConsultMessageResponse.from(consultMessageDtos));
    }

    @PatchMapping(path = "/rooms/{roomId}/name")
    public ResponseEntity<Void> updateRoomName(@PathVariable Long roomId, @RequestBody @Valid UpdateRoomNameRequest request) {
            log.info("채팅방 이름 수정 요청: {}, {}", roomId, request.getName());

        UpdateRoomNameCommand command = UpdateRoomNameCommand.of(roomId, request.getName());
        consultService.updateRoomName(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/rooms/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        log.info("채팅방 삭제 요청: {}", roomId);
        consultService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }
}