package com.homefit.homefit.region.controller;


import com.homefit.homefit.region.application.RegionService;
import com.homefit.homefit.region.application.dto.ShopCountDto;
import com.homefit.homefit.region.application.dto.SidoDto;
import com.homefit.homefit.region.controller.response.RegionResponse;
import com.homefit.homefit.region.controller.response.SearchShopCountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/region")
@RestController
public class RegionController implements RegionApiSpecification {
    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<RegionResponse> getRegions() {
        List<SidoDto> dtos = regionService.getAllSido();

        return ResponseEntity.ok(RegionResponse.of(dtos));
    }

    @GetMapping("/shop")
    public ResponseEntity<SearchShopCountResponse> searchShopNearBy(@RequestParam Double latitude, @RequestParam Double longitude) {
        log.info("좌표 인근 상가 조회 요청: lat={}, lng={}", latitude, longitude);

        List<ShopCountDto> dtos = regionService.searchSecondShopsNear(latitude, longitude);

        return ResponseEntity.ok(SearchShopCountResponse.of(dtos));
    }
}
