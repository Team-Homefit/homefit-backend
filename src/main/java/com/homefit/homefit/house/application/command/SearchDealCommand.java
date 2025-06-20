package com.homefit.homefit.house.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchDealCommand {
    private final Integer size;
    private final Integer page;
    private final String sequence;
    private final String sggCode;
    private final String umdCode;
    private final Double swLatitude;
    private final Double swLongitude;
    private final Double neLatitude;
    private final Double neLongitude;

    public static SearchDealCommand of(
            Integer size, Integer page,
            String sequence,
            String sggCode, String umdCode,
            Double swLatitude, Double swLongitude, Double neLatitude, Double neLongitude
    ) {
        return new SearchDealCommand(
                size, page, sequence, sggCode, umdCode, swLatitude, swLongitude, neLatitude, neLongitude
        );
    }
}
