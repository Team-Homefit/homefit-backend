package com.homefit.homefit.report.persistence.po;

import com.homefit.homefit.report.domain.ReportType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReportsPo {
    private Long reporteeId;
    private String reporteeUsername;
    private String reporteeNickname;
    private Long totalReportCount;
    private List<Source> sources;

    @Getter
    public static class Source {
        private Long targetSourceId;
        private ReportType type;
        private List<Report> reports;
    }

    @Getter
    public static class Report {
        private Long reportId;
        private LocalDateTime reportedAt;
        private Long reporterId;
        private String reporterUsername;
        private String reporterNickname;
    }

    @Override
    public String toString() {
        return "ReportsPo{" +
                "reporteeId=" + reporteeId +
                ", reporteeUsername=" + reporteeUsername +
                ", reporteeNickname=" + reporteeNickname +
                ", sources=" + sources +
                '}';
    }
}
