package com.homefit.homefit.house.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AreaDto {
	private final Double area;
	private final Integer square;
	
	public static AreaDto from(Double area) {
		return new AreaDto(area, (int) (area / 3.3));
	}
}
