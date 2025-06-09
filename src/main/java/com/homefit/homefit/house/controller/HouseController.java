package com.homefit.homefit.house.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homefit.homefit.house.application.HouseService;
import com.homefit.homefit.house.application.dto.AreaDto;
import com.homefit.homefit.house.application.dto.DealPageDto;
import com.homefit.homefit.house.application.dto.HouseDealDto;
import com.homefit.homefit.house.application.dto.HouseDto;
import com.homefit.homefit.house.controller.request.SearchDealRequest;
import com.homefit.homefit.house.controller.request.SearchHouseDealRequest;
import com.homefit.homefit.house.controller.response.SearchAreaResponse;
import com.homefit.homefit.house.controller.response.SearchDealResponse;
import com.homefit.homefit.house.controller.response.SearchHouseResponse;
import com.homefit.homefit.house.controller.response.SearchHouseDealResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/house")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Controller
public class HouseController implements HouseApiSpecification {
    private final HouseService houseService;

    @GetMapping("/{sequence}")
    public ResponseEntity<SearchHouseResponse> searchHouses(@PathVariable("sequence") String sequence) {
        log.info("주택 조회 요청: sequence={}", sequence);
        
        HouseDto house = houseService.searchHouse(sequence);
        
        return ResponseEntity.status(HttpStatus.OK).body(SearchHouseResponse.from(house));
    }

    @GetMapping("/deal")
    public ResponseEntity<SearchDealResponse> searchDeals(@ModelAttribute SearchDealRequest request) {
        log.info("주택 거래 조회 요청: {}", request);
        
        DealPageDto houseDeals = houseService.searchDeals(request);
        
        return ResponseEntity.status(HttpStatus.OK).body(SearchDealResponse.from(houseDeals));
    }
    
    @GetMapping("/{sequence}/area")
    public ResponseEntity<SearchAreaResponse> searchAreas(@PathVariable String sequence) {
        log.info("주택 면적 조회 요청: sequence = {}", sequence);
        
        List<AreaDto> areaDtos = houseService.searchAreas(sequence);
        
    	return ResponseEntity.ok(SearchAreaResponse.of(areaDtos));
    }
    
    @GetMapping("/{sequence}/deal")
    public ResponseEntity<SearchHouseDealResponse> searchHouseDeal(@PathVariable String sequence, @ModelAttribute SearchHouseDealRequest request) {
        log.info("주택 거래 조회 요청: sequence = {}, request = {}", sequence, request);
        
        HouseDealDto dtos = houseService.searchHouseDeal(sequence, request);
        
    	return ResponseEntity.ok(SearchHouseDealResponse.of(sequence, dtos));
    }
}
