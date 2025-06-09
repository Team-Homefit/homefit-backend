package com.homefit.homefit.house.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.homefit.homefit.house.controller.request.SearchDealRequest;
import com.homefit.homefit.house.controller.request.SearchHouseDealRequest;
import com.homefit.homefit.house.controller.response.SearchAreaResponse;
import com.homefit.homefit.house.controller.response.SearchDealResponse;
import com.homefit.homefit.house.controller.response.SearchHouseResponse;
import com.homefit.homefit.house.controller.response.SearchHouseDealResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "House", description = "주택과 주택 거래 정보 관리에 대한 API")
public interface HouseApiSpecification {

    @Operation(
        summary = "주택 정보 조회",
                description = """
                아파트 번호를 통해 아파트 정보를 조회합니다.
                sequence: 검색할 아파트 번호
                """,
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "주택 정보 조회 성공",
                        content = @Content(schema = @Schema(implementation = SearchHouseResponse.class), mediaType = "application/json")
                ),
                @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content())
        }
    )
    ResponseEntity<SearchHouseResponse> searchHouses(String sequence);

    @Operation(
        summary = "주택 거래 정보 조회",
        description = """
                원하는 조건을 통해 주택 거래 정보를 조회합니다.<br>
                조건(선택): sequence(아파트 번호), sgg-code(시군구코드 5자리), umd-code(읍면동 코드 5자리), sw_latitude&sw_longitude&ne_latitude%ne_longitudue(남서, 북동 위경도)<br>
                페이징(필수): size: 페이지 크기, page: 페이지 번호
                """,
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "주택 거래 정보 조회 성공",
                        content = @Content(schema = @Schema(implementation = SearchDealResponse.class), mediaType = "application/json")
                ),
                @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content())
        }
    )
    ResponseEntity<SearchDealResponse> searchDeals(SearchDealRequest request);
    @Operation(
            summary = "주택 면적 조회",
            description = "특정 아파트의 특정 평수 목록을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = SearchAreaResponse.class), mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 아파트", content = @Content())
            }
        )
    ResponseEntity<SearchAreaResponse> searchAreas(@PathVariable String sequence);

    @Operation(
        summary = "주택 거래 통계 조회",
        description = """
                특정 아파트의 특정 평수의 거래 통계를 조회합니다.
                거래일 내림차순으로 정렬해 반환합니다.
                """,
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "조회 성공",
                        content = @Content(schema = @Schema(implementation = SearchHouseDealResponse.class), mediaType = "application/json")
                ),
                @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content()),
                @ApiResponse(responseCode = "404", description = "존재하지 않는 아파트", content = @Content())
        }
    )
    ResponseEntity<SearchHouseDealResponse> searchHouseDeal(String sequence, SearchHouseDealRequest request);
}
