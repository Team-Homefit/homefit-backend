package com.homefit.homefit.report.application.command;

import com.homefit.homefit.report.domain.ReportType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportCommand {
    private final Long targetSourceId;
    private final ReportType type;

    public static ReportCommand of(Long targetSourceId, ReportType type) {
        return new ReportCommand(targetSourceId, type);
    }
}
