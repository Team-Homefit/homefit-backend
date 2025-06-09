package com.homefit.homefit.region.persistence.po;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShopCountPo {
    private String firstCategoryCode;
    private String firstCategoryName;
    private String secondCategoryCode;
    private String secondCategoryName;
    private String thirdCategoryCode;
    private String thirdCategoryName;
    private Integer shopCount;
}
