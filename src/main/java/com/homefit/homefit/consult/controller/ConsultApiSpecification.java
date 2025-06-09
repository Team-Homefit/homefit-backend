package com.homefit.homefit.consult.controller;

import com.homefit.homefit.consult.controller.request.AnalyzeContractRequest;
import com.homefit.homefit.consult.controller.response.AnalyzeContractResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "AI 채팅", description = "부동산 계약에 관한 AI 상담 기능")
public interface ConsultApiSpecification {
    @Operation(
            summary = "계약서 분석",
            description = "주어진 계약서 파일을 분석해 개선 사항을 안내합니다.",
            requestBody = @RequestBody(
                    required = true,
                    description = "분석할 계약서와 메시지 내용, 채팅방 ID, 첫 메시지 플래그",
                    content = @Content(mediaType = "application/form-data")
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "분석 성공",
                            content = @Content(schema = @Schema(implementation = AnalyzeContractResponse.class), mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content()),
                    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자의 요청", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음", content = @Content())
            }
    )
    ResponseEntity<AnalyzeContractResponse> analyzeContract(AnalyzeContractRequest request);
}
