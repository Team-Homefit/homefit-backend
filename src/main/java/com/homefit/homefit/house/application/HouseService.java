package com.homefit.homefit.house.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.house.application.command.SearchDealCommand;
import com.homefit.homefit.house.application.command.SearchHouseDealCommand;
import com.homefit.homefit.house.persistence.command.SearchHouseCondition;
import com.homefit.homefit.house.application.dto.AreaDto;
import com.homefit.homefit.house.application.dto.DealPageDto;
import com.homefit.homefit.house.application.dto.HouseDealDto;
import com.homefit.homefit.house.application.dto.HouseDto;
import com.homefit.homefit.house.persistence.HouseRepository;
import com.homefit.homefit.house.persistence.po.DealPagePo;
import com.homefit.homefit.house.persistence.po.HouseDealPo;
import com.homefit.homefit.house.persistence.po.HousePo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HouseService {
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
    public DealPageDto searchDeals(SearchDealCommand command) {
        SearchHouseCondition condition = SearchHouseCondition.of(
                command.getSequence(),
                command.getSggCode(),
                command.getUmdCode(),
                command.getSwLatitude(),
                command.getSwLongitude(),
                command.getNeLatitude(),
                command.getNeLongitude()
        );
        Integer offset = command.getSize() * (command.getPage() - 1);
        
        List<DealPagePo> dealPagePos = houseRepository.searchDealsByPage(command.getSize(), offset, condition);
        
        if (dealPagePos == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "아파트 거래 내력을 찾을 수 없습니다.");
        }
        
        return DealPageDto.of(dealPagePos, command.getSize(), command.getPage());
    }

    @PreAuthorize("hasAnyRole('BASIC')")
    public List<AreaDto> searchAreas(String sequence) {
    	List<Double> areas = houseRepository.searchAreasBySequence(sequence);

    	return areas.stream().map(AreaDto::from).toList();
    }

    @PreAuthorize("hasAnyRole('BASIC')")
    public HouseDealDto searchHouseDeal(SearchHouseDealCommand command) {
    	List<HouseDealPo> pos = houseRepository.searchHouseDealsBy(command.getSequence(), command.getArea(), command.getSince(), command.getUntil());
    	
    	return HouseDealDto.of(command.getSequence(), pos);
    }
}
