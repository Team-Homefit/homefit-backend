package com.homefit.homefit.region.controller.response;

import com.homefit.homefit.region.application.dto.ShopCountDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchShopCountResponse {
    private final List<ShopCount> shops;

    public static SearchShopCountResponse of(List<ShopCountDto> dtos) {
        return new SearchShopCountResponse(dtos.stream().map(ShopCount::from).toList());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class ShopCount {
        private final String code;
        private final String name;
        private final Integer count;

        public static ShopCount from(ShopCountDto dto) {
            return new ShopCount(dto.getCode(), dto.getName(), dto.getCount());
        }
    }
}
