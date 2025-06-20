package com.homefit.homefit.report.application.command;

import com.homefit.homefit.report.domain.BanDuration;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BanCommand {
    private final Long reporteeId;
    private final BanDuration duration;
    private final String reason;
    private final LocalDateTime now;

    public static BanCommand of(Long reporteeId, BanDuration duration, String reason) {
        return new BanCommand(reporteeId, duration, reason, LocalDateTime.now());
    }
}
