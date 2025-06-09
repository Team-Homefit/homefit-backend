package com.homefit.homefit.house.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchDealRequest {
	private Integer size;
	private Integer page;
	private String sequence;
	private String sggCode;
	private String umdCode;
	private Double swLatitude;
	private Double swLongitude;
	private Double neLatitude;
	private Double neLongitude;
	
	@Override
	public String toString() {
		return "SearchHouseDealRequest [size=" + size + ", page=" + page + ", sequence=" + sequence + ", sggCode="
				+ sggCode + ", umdCode=" + umdCode + ", swLatitude=" + swLatitude + ", swLongitude=" + swLongitude
				+ ", neLatitude=" + neLatitude + ", neLongitude=" + neLongitude + "]";
	}
}
