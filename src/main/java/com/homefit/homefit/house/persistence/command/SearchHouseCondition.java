package com.homefit.homefit.house.persistence.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchHouseCondition {
    private String sequence;
    private String sggCode;
    private String umdCode;
    private Double swLatitude;
    private Double swLongitude;
    private Double neLatitude;
    private Double neLongitude;

    public static SearchHouseCondition of(
            String sequence,
            String sggCode,
            String umdCode,
            Double swLatitude,
            Double swLongitude,
            Double neLatitude,
            Double neLongitude
    ) {
        return new SearchHouseCondition(sequence, sggCode, umdCode, swLatitude, swLongitude, neLatitude, neLongitude);
    }
}
