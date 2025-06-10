package com.homefit.homefit.report.controller.request;

import com.homefit.homefit.report.domain.ReportType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportRequest {
    @NotNull(message = "신고할 자원 ID는 필수입니다")
    private final Long targetSourceId;
    @NotNull(message = "신고할 자원 유형은 필수입니다")
    private final ReportType type;

    @Override
    public String toString() {
        return "ReportRequest{" +
                "targetSourceId=" + targetSourceId +
                ", type=" + type +
                '}';
    }
}
