package com.homefit.homefit.house.controller.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchDealRequest {
	private final Integer size;
	private final Integer page;
	private final String sequence;
	private final String sggCode;
	private final String umdCode;
	private final Double swLatitude;
	private final Double swLongitude;
	private final Double neLatitude;
	private final Double neLongitude;
	
	@Override
	public String toString() {
		return "SearchHouseDealRequest [size=" + size + ", page=" + page + ", sequence=" + sequence + ", sggCode="
				+ sggCode + ", umdCode=" + umdCode + ", swLatitude=" + swLatitude + ", swLongitude=" + swLongitude
				+ ", neLatitude=" + neLatitude + ", neLongitude=" + neLongitude + "]";
	}
}
