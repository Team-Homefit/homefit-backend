package com.homefit.homefit.report.application.dto;

import com.homefit.homefit.report.persistence.po.BanPo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BanDto {
    private final Long banId;
    private final Long bannedMemberId;
    private final LocalDateTime startAt;
    private final LocalDateTime expireAt;
    private final String reason;
    private final Boolean submitApology;

    public static BanDto from(BanPo po) {
        return new BanDto(
                po.getBanId(),
                po.getBannedMemberId(),
                po.getStartAt(),
                po.getExpireAt(),
                po.getReason(),
                po.getSubmitApology()
        );
    }
}
