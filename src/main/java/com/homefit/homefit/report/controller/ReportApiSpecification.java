package com.homefit.homefit.report.controller;

import com.homefit.homefit.report.controller.request.ApologyRequest;
import com.homefit.homefit.report.controller.request.BanRequest;
import com.homefit.homefit.report.controller.request.ReportRequest;
import com.homefit.homefit.report.controller.response.BanResponse;
import com.homefit.homefit.report.controller.response.ReportsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;

@Tag(name = "신고", description = "신고 및 정지 관련 API")
public interface ReportApiSpecification {
    @Operation(summary = "신고", description = "게시글 혹은 댓글의 작성자를 신고합니다.", requestBody = @RequestBody(required = true, description = """
            신고할 게시글 혹은 댓글의 ID<br>
            신고 유형: ARTICLE, COMMENT
            """, content = @Content(mediaType = "application/json")), responses = {
            @ApiResponse(responseCode = "200", description = "신고 성공", content = @Content()),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content())
    })
    ResponseEntity<Void> report(ReportRequest request);

    @Operation(summary = "신고 내역 조회 (어드민 전용)", description = "아직 처리되지 않은 신고 내역을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "정지 성공", content = @Content(schema = @Schema(implementation = ReportsResponse.class), mediaType = "application/json"))
    })
    ResponseEntity<ReportsResponse> getReports();

    @Operation(summary = "사용자 정지 (어드민 전용)", description = "주어진 사용자의 권한을 특정 기간 동안 BANNED로 설정합니다.", requestBody = @RequestBody(required = true, description = """
            정지할 사용자 ID<br>
            정지 기간: DAY, THREE_DAY, MONTH(30일)
            """, content = @Content(mediaType = "application/json")), responses = {
            @ApiResponse(responseCode = "200", description = "정지 성공", content = @Content()),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content())
    })
    ResponseEntity<Void> ban(BanRequest banRequest);

    @Operation(summary = "현재 정지건 조회", description = "요청한 사용자에 대해 현재 진행 중인 정지 내역을 조회합니다", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = BanResponse.class), mediaType = "application/json"))
    })
    ResponseEntity<BanResponse> getMyBan();

    @Operation(summary = "반성문 제출", description = """
            현재 사용자가 반성문을 제출합니다.<br>
            정지 기간이 끝난 경우 권한이 회복됩니다.<br>
            """, responses = {
            @ApiResponse(responseCode = "200", description = "반성문 제출 성공", content = @Content()),
            @ApiResponse(responseCode = "400", description = "잘못된 요청(이미 반성문을 제출한 산고)", content = @Content()),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 신고", content = @Content())
    })
    ResponseEntity<Void> apology(ApologyRequest request);
}
