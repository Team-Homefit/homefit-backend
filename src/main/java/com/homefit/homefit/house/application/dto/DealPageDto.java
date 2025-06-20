package com.homefit.homefit.house.application.dto;

import java.util.List;

import com.homefit.homefit.house.persistence.po.DealPagePo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DealPageDto {
    private final List<HouseDeal> houseDeals;
    private final Integer pageSize;
    private final Integer page;
    private final Integer leftPages;

    public static DealPageDto of(List<DealPagePo> pos, Integer size, Integer page) {
        return new DealPageDto(
                pos.stream().limit(size).map(HouseDeal::from).toList(),
                size,
                page,
                pos.size() / size
        );
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
        
        public static HouseDeal from(DealPagePo po) {
            return new HouseDeal(
                    po.getNo(),
                    po.getAptSeq(),
                    po.getAptNm(),
                    po.getAptDong(),
                    po.getFloor(),
                    po.getBuildYear(),
                    po.getExcluUseAr(),
                    po.getSggCd(),
                    po.getUmdCd(),
                    po.getLatitude(),
                    po.getLongitude(),
                    po.getDealAmount(),
                    po.getDealYear(),
                    po.getDealMonth(),
                    po.getDealDay()
            );
        }
    }
}
