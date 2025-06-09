package com.homefit.homefit.member.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddInterestedRegionsResponse {
	private final Integer numberOfAdd;
	
	public static AddInterestedRegionsResponse of(Integer numberOfAdd) {
		return new AddInterestedRegionsResponse(numberOfAdd);
	}
}
