package com.homefit.homefit.member.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.homefit.homefit.member.domain.InterestedRegion;
import com.homefit.homefit.member.persistence.po.InterestedRegionWithSggPo;

@Mapper
@Repository
public interface InterestedRegionRepository {
	int insertAllIgnoreDuplicate(List<InterestedRegion> interestedRegions);
	
	int countByMember(Long memberId);
	
	InterestedRegionWithSggPo selectAllByMember(Long memberId);
	
	int deleteAll(Long memberId, List<String> sggCodes);
}
