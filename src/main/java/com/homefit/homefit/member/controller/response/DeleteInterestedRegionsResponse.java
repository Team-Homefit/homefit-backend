package com.homefit.homefit.member.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteInterestedRegionsResponse {
	private final Integer numberOfDelete;
	
	public static DeleteInterestedRegionsResponse of(Integer numberOfDelete) {
		return new DeleteInterestedRegionsResponse(numberOfDelete);
	}
}
