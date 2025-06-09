package com.homefit.homefit.region.controller;

import com.homefit.homefit.region.controller.response.RegionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "지역", description = "지역 정보와 관심 지역에 관한 API")
public interface RegionApiSpecification {
    @Operation(summary = "지역 정보 조회", description = """
            전국의 지역 정보를 읍면동 단위로 조회합니다.<br>
            sido의 코드: 2자리 숫자<br>
            sgg의 코드: 5자리 숫자 (시도코드 2자리 포함)<br>
            umd의 코드: 10자리 숫자 (시군구코드 5자리 포함)<br>""", responses = {
            @ApiResponse(responseCode = "200", description = "지역 정보 조회 성공", content = @Content(schema = @Schema(implementation = RegionResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content())
    })
    ResponseEntity<RegionResponse> getRegions();
}
