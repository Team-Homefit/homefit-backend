package com.homefit.homefit.report.controller.response;

import com.homefit.homefit.report.application.dto.ReportStatisticDto;
import com.homefit.homefit.report.domain.ReportType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportsResponse {
    private final List<Reportee> reportees;

    public static ReportsResponse of(List<ReportStatisticDto> dtos) {
        return new ReportsResponse(dtos.stream().map(Reportee::from).toList());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Reportee {
        private final Long reporteeId;
        private final String reporteeUsername;
        private final String reporteeNickname;
        private final List<Source> sources;
        private final Long totalReportCount;

        public static Reportee from(ReportStatisticDto dto) {
            return new Reportee(
                    dto.getReporteeId(),
                    dto.getReporteeUsername(),
                    dto.getReporteeNickname(),
                    dto.getSources().stream().map(Source::from).toList(),
                    dto.getTotalReportCount()
            );
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Source {
        private final Long targetSourceId;
        private final ReportType type;
        private final List<Report> reports;

        public static Source from(ReportStatisticDto.Source dto) {
            return new Source(
                    dto.getTargetSourceId(),
                    dto.getType(),
                    dto.getReports().stream().map(Report::from).toList()
            );
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Report {
        private final Long reportId;
        private final Long reporterId;
        private final String reporterUsername;
        private final String reporterNickname;

        public static Report from(ReportStatisticDto.Report dto) {
            return new Report(
                    dto.getReportId(),
                    dto.getReporterId(),
                    dto.getReporterUsername(),
                    dto.getReporterNickname()
            );
        }
    }
}
