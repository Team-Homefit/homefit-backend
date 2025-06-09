package com.homefit.homefit.report.persistence;

import com.homefit.homefit.report.domain.Ban;
import com.homefit.homefit.report.persistence.po.BanPo;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BanRepository {
    int insert(Ban ban);
    
    BanPo selectById(Long id);
    
    BanPo selectByMemberYetDoneUntil(Long memberId, LocalDateTime until);
    
    int updateSubmitApology(Ban ban);
}
