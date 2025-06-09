package com.homefit.homefit.report.application.dto;

import com.homefit.homefit.report.domain.ReportType;
import com.homefit.homefit.report.persistence.po.ReportsPo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportStatisticDto {
    private final Long reporteeId;
    private final String reporteeUsername;
    private final String reporteeNickname;
    private final Long totalReportCount;
    private final List<Source> sources;

    public static ReportStatisticDto from(ReportsPo reportsPo) {
        return new ReportStatisticDto(
                reportsPo.getReporteeId(),
                reportsPo.getReporteeUsername(),
                reportsPo.getReporteeNickname(),
                reportsPo.getTotalReportCount(),
                reportsPo.getSources().stream().map(Source::from).toList()
        );
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Source {
        private final Long targetSourceId;
        private final ReportType type;
        private final List<Report> reports;

        public static Source from(ReportsPo.Source po) {
            return new Source(
                    po.getTargetSourceId(),
                    po.getType(),
                    po.getReports().stream().map(Report::from).toList()
            );
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Report {
        private final Long reportId;
        private final LocalDateTime reportedAt;
        private final Long reporterId;
        private final String reporterUsername;
        private final String reporterNickname;

        public static Report from(ReportsPo.Report po) {
            return new Report(
                    po.getReportId(),
                    po.getReportedAt(),
                    po.getReporterId(),
                    po.getReporterUsername(),
                    po.getReporterNickname()
            );
        }
    }
}
