package com.homefit.homefit.house.controller.response;

import java.util.List;

import com.homefit.homefit.house.application.dto.DealPageDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchDealResponse {
	private final List<HouseDeal> deals;
	private final Integer page;
	private final Integer pageSize;
	private final Integer totalPages;
	private final Long totalElements;
	
	public static SearchDealResponse from(DealPageDto dto) {
		return new SearchDealResponse(
				dto.getHouseDeals().stream().map(SearchDealResponse.HouseDeal::from).toList(),
				dto.getPage(),
				dto.getPageSize(),
				dto.getTotalPages(),
				dto.getTotalElements());
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class HouseDeal {
        private final Integer no;
        private final String aptSequence;
        private final String aptName;
        private final String aptDong;
        private final String floor;
        private final Integer buildYear;
        private final Double excluUseArea;
        private final String sggCode;
        private final String umdCode;
        private final Double latitude;
        private final Double longitude;
        private final String dealAmount;
        private final Integer dealYear;
        private final Integer dealMonth;
        private final Integer dealDay;
        
        public static HouseDeal from(DealPageDto.HouseDeal dto) {
        	return new HouseDeal(
        			dto.getNo(),
                    dto.getAptSequence(),
                    dto.getAptName(),
                    dto.getAptDong(),
                    dto.getFloor(),
                    dto.getBuildYear(),
                    dto.getExcluUseArea(),
                    dto.getSggCode(),
                    dto.getUmdCode(),
                    dto.getLatitude(),
                    dto.getLongitude(),
                    dto.getDealAmount(),
                    dto.getDealYear(),
                    dto.getDealMonth(),
                    dto.getDealDay()
			);
        }
	}
}
