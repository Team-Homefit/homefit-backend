package com.homefit.homefit.member.controller.response;

import java.util.List;

import com.homefit.homefit.member.application.dto.InterestedRegionDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchInterestedRegionResponse {
	private final List<Sido> sidos;
	
	public static SearchInterestedRegionResponse from(InterestedRegionDto dto) {
		return new SearchInterestedRegionResponse(dto.getSidos().stream().map(Sido::from).toList());
	}
	
	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	private static class Sido {
		private final String code;
		private final String name;
		private final List<Sgg> sggs;
		
		public static Sido from(InterestedRegionDto.Sido dto) {
			return new Sido(dto.getCode(), dto.getName(), dto.getSggs().stream().map(Sgg::from).toList());
		}
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	private static class Sgg {
		private final String code;
		private final String name;
		private final Long boardId;
		
		public static Sgg from(InterestedRegionDto.Sgg dto) {
			return new Sgg(dto.getCode(), dto.getName(), dto.getBoardId());
		}
	}
}
