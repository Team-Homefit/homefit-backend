package com.homefit.homefit.member.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.member.application.dto.InterestedRegionDto;
import com.homefit.homefit.member.controller.request.AddInterestedRegionsRequest;
import com.homefit.homefit.member.controller.request.DeleteInterestedRegionRequest;
import com.homefit.homefit.member.domain.InterestedRegion;
import com.homefit.homefit.member.persistence.InterestedRegionRepository;
import com.homefit.homefit.member.persistence.po.InterestedRegionWithSggPo;
import com.homefit.homefit.security.util.UserPrincipalUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InterestedRegionService {
	private final InterestedRegionRepository interestedRegionRepository;
	private static final Integer MAX_INTERESTED_REGION = 5;

	@Transactional
	@PreAuthorize("hasAnyRole('ADMIN', 'BASIC')")
	public Integer addInterestedRegions(AddInterestedRegionsRequest request) {
		Long memberId = UserPrincipalUtil.getId()
				.orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 사용자를 찾을 수 없습니다"));
		
		List<InterestedRegion> interestedRegions = request.getSggCodes().stream()
				.map((code) -> InterestedRegion.of(memberId, code))
				.toList();
		
		int result = interestedRegionRepository.insertAllIgnoreDuplicate(interestedRegions);
		
		int totalCount = interestedRegionRepository.countByMember(memberId);
		if (totalCount > MAX_INTERESTED_REGION) {
			throw new HomefitException(HttpStatus.BAD_REQUEST, "관심지역을 5개 이상 등록할 수 없습니다");
		}
		
		return result;
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'BASIC')")
	public InterestedRegionDto searchInterestedRegions() {
		Long memberId = UserPrincipalUtil.getId()
				.orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 사용자를 찾을 수 없습니다"));
		
		InterestedRegionWithSggPo interestedRegionPos = interestedRegionRepository.selectAllByMember(memberId);
		
		return InterestedRegionDto.of(interestedRegionPos, memberId);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ADMIN', 'BASIC')")
	public Integer deleteInterestedRegions(DeleteInterestedRegionRequest request) {
		Long memberId = UserPrincipalUtil.getId()
				.orElseThrow(() -> new HomefitException(HttpStatus.FORBIDDEN, "현재 사용자를 찾을 수 없습니다"));
		
		int result = interestedRegionRepository.deleteAll(memberId, request.getSggCodes());
		
		return result;
	}
}
