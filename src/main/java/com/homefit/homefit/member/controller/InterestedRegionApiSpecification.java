package com.homefit.homefit.member.controller;

import org.springframework.http.ResponseEntity;

import com.homefit.homefit.member.controller.request.AddInterestedRegionsRequest;
import com.homefit.homefit.member.controller.request.DeleteInterestedRegionRequest;
import com.homefit.homefit.member.controller.response.AddInterestedRegionsResponse;
import com.homefit.homefit.member.controller.response.DeleteInterestedRegionsResponse;
import com.homefit.homefit.member.controller.response.SearchInterestedRegionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="관심지역", description = "사용자의 관심지역 관련 API")
public interface InterestedRegionApiSpecification {

    @Operation(
        summary = "관심지역 추가",
        description = "요청한 사용자의 관심 지역을 추가합니다. 이미 관심지역으로 설정한 지역은 무시합니다.",
        requestBody = @RequestBody(
                required = true,
                description = "관심지역으로 등록할 지역의 5자리 시군구 코드의 목록",
                content = @Content(mediaType = "application/json")
        ),
        responses = {
                @ApiResponse(
                		responseCode = "200",
                		description = "추가 성공. 추가된 관심지역 개수",
                		content = @Content(schema = @Schema(implementation = AddInterestedRegionsResponse.class), mediaType = "application/json")),
                @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content())
        }
    )
	ResponseEntity<AddInterestedRegionsResponse> addRegions(@RequestBody AddInterestedRegionsRequest request);

    @Operation(
            summary = "관심지역 조회",
            description = "요청한 사용자의 관심 지역을 조회합니다. 관심지역이 없는 경우 빈 배열이 반환됩니다.",
            responses = {
            		@ApiResponse(
                    		responseCode = "200",
                    		description = "조회 결과",
                    		content = @Content(schema = @Schema(implementation = SearchInterestedRegionResponse.class), mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content())
            }
    )
    ResponseEntity<SearchInterestedRegionResponse> searchRegions();
    
    @Operation(
        summary = "관심지역 삭제",
        description = "요청한 사용자의 관심 지역을 삭제합니다. 관심지역이 아니었던 지역은 무시합니다.",
        requestBody = @RequestBody(
                required = true,
                description = "관심지역을 삭제할 지역의 5자리 시군구 코드의 목록",
                content = @Content(mediaType = "application/json")
        ),
        responses = {
        		@ApiResponse(
                		responseCode = "200",
                		description = "삭제 성공, 삭제된 관심지역 개수",
                		content = @Content(schema = @Schema(implementation = DeleteInterestedRegionsResponse.class), mediaType = "application/json")),
                @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content())
        }
    )
	ResponseEntity<DeleteInterestedRegionsResponse> deleteRegions(@RequestBody DeleteInterestedRegionRequest request);
}
