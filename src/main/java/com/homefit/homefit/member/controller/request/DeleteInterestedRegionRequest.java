package com.homefit.homefit.member.controller.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteInterestedRegionRequest {
	@NotEmpty(message = "시군구 코드는 1개 이상 포함되어야 합니다")
	private final List<String> sggCodes;

	@Override
	public String toString() {
		return "DeleteInterestedRegionRequest [sggCodes=" + sggCodes + "]";
	}
}
