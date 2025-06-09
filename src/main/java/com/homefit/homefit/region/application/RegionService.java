package com.homefit.homefit.region.application;

import com.homefit.homefit.region.application.dto.ShopCountDto;
import com.homefit.homefit.region.application.dto.SidoDto;
import com.homefit.homefit.region.persistence.RegionRepository;
import com.homefit.homefit.region.persistence.ShopRepository;
import com.homefit.homefit.region.persistence.po.ShopCountPo;
import com.homefit.homefit.region.persistence.po.SidoPo;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionService {
    private final RegionRepository regionRepository;
    private final ShopRepository shopRepository;

    public List<SidoDto> getAllSido() {
        List<SidoPo> pos = regionRepository.selectAll();

        return pos.stream()
                .map(SidoDto::from)
                .toList();
    }

    @PreAuthorize("hasAnyRole('BASIC', 'ADMIN')")
    public List<ShopCountDto> searchSecondShopsNear(Double latitude, Double longitude) {
        /**
    	 * Q1: 보건의료
    	 * G204: 종합소매, P105: 일반 교육,  I211: 주점, R104: 유원지·오락
    	 * G21501: 약국, 
    	 */
        Double km = 0.2;
        List<String> firstCodes = List.of("Q1");
        List<String> secondCodes = List.of("G204", "P105", "I211", "R104");
        List<String> thirdCodes = List.of("G21501");

        List<ShopCountPo> pos = shopRepository.selectNearKm(latitude, longitude, km, firstCodes, secondCodes, thirdCodes);

        return pos.stream().map(ShopCountDto::from).toList();
    }
}
