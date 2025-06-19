package com.homefit.homefit.house.application;

import java.util.List;

import com.homefit.homefit.house.persistence.command.SearchHouseCondition;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.house.application.dto.AreaDto;
import com.homefit.homefit.house.application.dto.DealPageDto;
import com.homefit.homefit.house.application.dto.HouseDealDto;
import com.homefit.homefit.house.application.dto.HouseDto;
import com.homefit.homefit.house.controller.request.SearchDealRequest;
import com.homefit.homefit.house.controller.request.SearchHouseDealRequest;
import com.homefit.homefit.house.persistence.HouseRepository;
import com.homefit.homefit.house.persistence.po.DealPagePo;
import com.homefit.homefit.house.persistence.po.HouseDealPo;
import com.homefit.homefit.house.persistence.po.HousePo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class    HouseService {
    private final HouseRepository houseRepository;

    @PreAuthorize("hasAnyRole('BASIC')")
    public HouseDto searchHouse(String houseSeq) {
        HousePo housePo = houseRepository.searchHouseBySequence(houseSeq);
        if (housePo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "아파트를 찾을 수 없습니다.");
        }
        
        return HouseDto.from(housePo);
    }

    @PreAuthorize("hasAnyRole('BASIC')")
    public DealPageDto searchDeals(SearchDealRequest request) {
        Integer offset = request.getSize() * (request.getPage() - 1);

        SearchHouseCondition condition = SearchHouseCondition.of(
                request.getSequence(),
                request.getSggCode(),
                request.getUmdCode(),
                request.getSwLatitude(),
                request.getSwLongitude(),
                request.getNeLatitude(),
                request.getNeLongitude()
        );
        
        List<DealPagePo> dealPagePos = houseRepository.searchDealsByPage(request.getSize() * 5, offset, condition);
        
        if (dealPagePos == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "아파트 거래 내력을 찾을 수 없습니다.");
        }
        
        return DealPageDto.of(dealPagePos, request.getSize(), request.getPage());
    }

    @PreAuthorize("hasAnyRole('BASIC')")
    public List<AreaDto> searchAreas(String sequence) {
    	List<Double> areas = houseRepository.searchAreasBySequence(sequence);

    	return areas.stream().map(AreaDto::from).toList();
    }

    @PreAuthorize("hasAnyRole('BASIC')")
    public HouseDealDto searchHouseDeal(String sequence, SearchHouseDealRequest request) {
    	List<HouseDealPo> pos = houseRepository.searchHouseDealsBy(sequence, request.getArea(), request.getSince(), request.getUntil());
    	
    	return HouseDealDto.of(sequence, pos);
    }
}
