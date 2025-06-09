package com.homefit.homefit.region.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Shop {
    private String code;
    private String name;
    private String branchName;
    private Double latitude;
    private Double longitude;
    private String umdCode;
    private String roadnameAddress;
    private String jibunAddress;
    private String firstCategoryCode;
    private String firstCategoryName;
    private String secondCategoryCode;
    private String secondCategoryName;
    private String thirdCategoryCode;
    private String thirdCategoryName;

    @Override
    public String toString() {
        return "Shop{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", branchName='" + branchName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", umdCode='" + umdCode + '\'' +
                ", roadnameAddress='" + roadnameAddress + '\'' +
                ", jibunAddress='" + jibunAddress + '\'' +
                ", firstCategoryCode='" + firstCategoryCode + '\'' +
                ", firstCategoryName='" + firstCategoryName + '\'' +
                ", secondCategoryCode='" + secondCategoryCode + '\'' +
                ", secondCategoryName='" + secondCategoryName + '\'' +
                ", thirdCategoryCode='" + thirdCategoryCode + '\'' +
                ", thirdCategoryName='" + thirdCategoryName + '\'' +
                '}';
    }
}
