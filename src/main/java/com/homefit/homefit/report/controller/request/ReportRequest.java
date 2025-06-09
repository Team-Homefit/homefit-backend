package com.homefit.homefit.report.controller.request;

import com.homefit.homefit.report.domain.ReportType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportRequest {
    @NotNull
    private final Long targetSourceId;
    @NotNull
    private final ReportType type;

    @Override
    public String toString() {
        return "ReportRequest{" +
                "targetSourceId=" + targetSourceId +
                ", type=" + type +
                '}';
    }
}
