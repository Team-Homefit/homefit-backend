package com.homefit.homefit.report.persistence.po;

import java.time.LocalDateTime;

import com.homefit.homefit.report.domain.Ban;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BanPo {
	private final Long banId;
	private final Long bannedMemberId;
	private final LocalDateTime startAt;
	private final LocalDateTime expireAt;
	private final Boolean submitApology;
	private final String reason;

	public Ban toDomain() {
		return Ban.of(banId, bannedMemberId, startAt, expireAt, reason, submitApology);
	}
}
