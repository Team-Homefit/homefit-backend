package com.homefit.homefit.house.controller.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchHouseDealRequest {
	private Double area;
	private LocalDate since;
	private LocalDate until;
}
