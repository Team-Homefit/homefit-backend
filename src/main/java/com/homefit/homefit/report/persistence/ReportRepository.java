package com.homefit.homefit.report.persistence;

import com.homefit.homefit.report.domain.Report;
import com.homefit.homefit.report.persistence.po.ReportPo;

import java.time.LocalDateTime;
import java.util.List;

import com.homefit.homefit.report.persistence.po.ReportsPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReportRepository {
    int insert(Report report);

    int countDuplicateSinceYesterday(Report report);

    List<ReportsPo> selectAllYetDoneUntil();
    
    List<ReportPo> selectAllByReporteeUntilYetDone(Long reporteeId, LocalDateTime until);
    
    int updatePass(List<Report> reports);
    
    int updateBan(List<Report> reports);
}
