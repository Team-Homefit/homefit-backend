package com.homefit.homefit.house.controller.response;

import java.util.List;

import com.homefit.homefit.house.application.dto.HouseDealDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchHouseDealResponse {
	private final String aptSequence;
	private final Double avgDealAmount;
	private final List<Deal> deals;
	
	public static SearchHouseDealResponse of(String sequence, HouseDealDto dto) {
		return new SearchHouseDealResponse(sequence, dto.getAvgAmount(), dto.getDeals().stream().map(Deal::from).toList());
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	static class Deal {
        private final Long dealAmount;
        private final Integer dealYear;
        private final Integer dealMonth;
        private final Integer dealDay;
        private final String aptDong;
        private final String floor;
        private final Double area;
        
        static Deal from(HouseDealDto.Deal dto) {
        	return new Deal(dto.getDealAmount(), dto.getDealYear(), dto.getDealMonth(), dto.getDealDay(), dto.getAptDong(), dto.getFloor(), dto.getArea());
        }
	}
}
