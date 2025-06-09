package com.homefit.homefit.report.persistence.po;

import com.homefit.homefit.report.domain.Report;
import com.homefit.homefit.report.domain.ReportType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportPo {
    private final Long reportId;
    private final Long reporterId;
    private final Long targetSourceId;
    private final ReportType type;
    private final Boolean isPassed;
    private final LocalDateTime reportedAt;
    private final Long banId;

    public Report toDomain() {
        return Report.of(reportId, reporterId, banId, targetSourceId, type, reportedAt, isPassed);
    }
}
