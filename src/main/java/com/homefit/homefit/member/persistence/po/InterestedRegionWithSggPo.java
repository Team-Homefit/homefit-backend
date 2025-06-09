package com.homefit.homefit.member.persistence.po;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterestedRegionWithSggPo {
	private Long memberId;
	private List<Sido> sidos;

	@Getter
	@Setter
	public static class Sido {
		private String code;
		private String name;
		private List<Sgg> sggs;
	}

	@Getter
	@Setter
	public static class Sgg {
		private String code;
		private String name;
		private Long boardId;
	}
}
