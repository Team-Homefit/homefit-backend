package com.homefit.homefit.house.persistence.po;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DealPagePo {
    private final Integer no;
    private final String aptSeq;
    private final String aptNm;
    private final String aptDong;
    private final String floor;
    private final Integer buildYear;
    private final Double excluUseAr;
    
    private final String sggCd;
    private final String umdCd;
    private final Double latitude;
    private final Double longitude;
    
    private final String dealAmount;
    private final Integer dealYear;
    private final Integer dealMonth;
    private final Integer dealDay;
}
