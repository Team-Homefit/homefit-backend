package com.homefit.homefit.report.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BanDuration {
    ZERO(0), DAY(1), THREE_DAY(1), MONTH(30);

    private int days;
}
