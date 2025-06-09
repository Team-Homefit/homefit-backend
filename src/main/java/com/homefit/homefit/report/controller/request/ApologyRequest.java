package com.homefit.homefit.report.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApologyRequest {
	@NotNull
	private final Long banId;

	@Override
	public String toString() {
		return "ApologyRequest [banId=" + banId + "]";
	}
}
