package com.homefit.homefit.house.persistence.po;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HouseDealPo {
    private final String dealAmount;
    private final Integer dealYear;
    private final Integer dealMonth;
    private final Integer dealDay;
    private final String aptDong;
    private final String floor;
    private final Double excluUseAr;
}
