package com.homefit.homefit.house.controller.response;

import com.homefit.homefit.house.application.dto.HouseDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchHouseResponse {
	private final String aptSequence;
	private final String sggCode;
	private final String umdCode;
	private final String umdName;
	private final String jibun;
	private final String roadNameSggCode;
	private final String roadName;
	private final String roadNameBonbun;
	private final String roadNameBubub;
	private final String aptName;
	private final Integer buildYear;
	private final Double latitude;
	private final Double longitude;
	private final String image;

	public static SearchHouseResponse from(HouseDto dto) {
	    return new SearchHouseResponse(
	        dto.getAptSequence(),
	        dto.getSggCode(),
	        dto.getUmdCode(),
	        dto.getUmdName(),
	        dto.getJibun(),
	        dto.getRoadNameSggCode(),
	        dto.getRoadName(),
	        dto.getRoadNameBonbun(),
	        dto.getRoadNameBubub(),
	        dto.getAptName(),
	        dto.getBuildYear(),
	        dto.getLatitude(),
	        dto.getLongitude(),
	        dto.getImage()
	    );
	}
}
