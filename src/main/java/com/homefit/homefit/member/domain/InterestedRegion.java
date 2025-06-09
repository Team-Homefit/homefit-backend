package com.homefit.homefit.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InterestedRegion {
	private Long memberId;
	private String sggCode;
	
	public static InterestedRegion of(Long memberId, String sggCode) {
		return new InterestedRegion(memberId, sggCode);
	}
}
