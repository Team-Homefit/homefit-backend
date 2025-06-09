package com.homefit.homefit.house.controller.response;

import java.util.List;

import com.homefit.homefit.house.application.dto.AreaDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchAreaResponse {
	private final List<Area> areas;
	
	public static SearchAreaResponse of(List<AreaDto> areaDtos) {
		return new SearchAreaResponse(areaDtos.stream().map(Area::from).toList());
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	static class Area {
		private final Double area;
		private final Integer square;
		
		static Area from(AreaDto dto) {
			return new Area(dto.getArea(), dto.getSquare());
		}
	}
}
