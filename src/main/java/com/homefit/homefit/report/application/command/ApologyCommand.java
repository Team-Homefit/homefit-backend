package com.homefit.homefit.report.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApologyCommand {
    private final Long banId;
    private final LocalDateTime now;

    public static ApologyCommand of(Long banId) {
        return new ApologyCommand(banId, LocalDateTime.now());
    }
}
