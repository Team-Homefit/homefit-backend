package com.homefit.homefit.region.application.dto;

import com.homefit.homefit.region.persistence.po.ShopCountPo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShopCountDto {
    private final String code;
    private final String name;
    private final Integer count;

    public static ShopCountDto from(ShopCountPo po) {
        return new ShopCountDto(
                po.getThirdCategoryCode() != null ? po.getThirdCategoryCode() : (po.getSecondCategoryCode() != null ? po.getSecondCategoryCode() : po.getFirstCategoryCode()),
                po.getThirdCategoryName() != null ? po.getThirdCategoryName() : (po.getSecondCategoryName() != null ? po.getSecondCategoryName() : po.getFirstCategoryName()),
                po.getShopCount());
    }
}
