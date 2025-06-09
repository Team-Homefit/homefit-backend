package com.homefit.homefit.member.application.dto;

import java.util.ArrayList;
import java.util.List;
import com.homefit.homefit.member.persistence.po.InterestedRegionWithSggPo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InterestedRegionDto {
	private final Long memberId;
	private final List<Sido> sidos;

	public static InterestedRegionDto of(InterestedRegionWithSggPo po, Long memberId) {		
		return new InterestedRegionDto(
				memberId,
				po == null ? new ArrayList<>() : po.getSidos().stream().map(Sido::from).toList());
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Sido{
		private final String code;
		private final String name;
		private final List<Sgg> sggs;
		
		public static Sido from(InterestedRegionWithSggPo.Sido po) {
			return new Sido(po.getCode(), po.getName(), po.getSggs().stream().map(Sgg::from).toList());
		}
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Sgg{
		private final String code;
		private final String name;
		private final Long boardId;
		
		public static Sgg from(InterestedRegionWithSggPo.Sgg po) {
			return new Sgg(po.getCode(), po.getName(), po.getBoardId());
		}
	}
}
