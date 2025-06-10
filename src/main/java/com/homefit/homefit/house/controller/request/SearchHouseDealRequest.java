package com.homefit.homefit.house.controller.request;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchHouseDealRequest {
	private final Double area;
	private final LocalDate since;
	private final LocalDate until;
}
