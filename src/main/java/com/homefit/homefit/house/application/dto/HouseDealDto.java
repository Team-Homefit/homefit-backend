package com.homefit.homefit.house.application.dto;

import java.util.ArrayList;
import java.util.List;

import com.homefit.homefit.house.persistence.po.HouseDealPo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HouseDealDto {
	private final String sequence;
	private final Double avgAmount;
	private final List<Deal> deals;
	
	public static HouseDealDto of(String sequence, List<HouseDealPo> pos) {
		List<Deal> deals = new ArrayList<>();
		Double sumOfAmount = 0.0;
		for (HouseDealPo po : pos) {
			Deal deal = Deal.from(po);
			deals.add(deal);
			sumOfAmount += deal.getDealAmount();
		}
		
		return new HouseDealDto(
				sequence,
				sumOfAmount / (deals.size() == 0 ? 1 : deals.size()),
				pos.stream().map(Deal::from).toList());
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Deal {
        private final Long dealAmount;
        private final Integer dealYear;
        private final Integer dealMonth;
        private final Integer dealDay;
        private final String aptDong;
        private final String floor;
        private final Double area;

        public static Deal from(HouseDealPo po) {
        	return new Deal(Long.parseLong(po.getDealAmount().replace(",", "")), po.getDealYear(), po.getDealMonth(), po.getDealDay(), po.getAptDong(), po.getFloor(), po.getExcluUseAr());
        }
    }
    
}
