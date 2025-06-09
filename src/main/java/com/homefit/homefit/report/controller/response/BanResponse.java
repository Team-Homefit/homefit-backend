package com.homefit.homefit.report.controller.response;

import com.homefit.homefit.report.application.dto.BanDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BanResponse {
    private final Long banId;
    private final Long bannedMemberId;
    private final LocalDateTime startAt;
    private final LocalDateTime expireAt;
    private final String reason;
    private final Boolean submitApology;

    public static BanResponse from(BanDto dto) {
        return new BanResponse(
                dto.getBanId(),
                dto.getBannedMemberId(),
                dto.getStartAt(),
                dto.getExpireAt(),
                dto.getReason(),
                dto.getSubmitApology());
    }
}
