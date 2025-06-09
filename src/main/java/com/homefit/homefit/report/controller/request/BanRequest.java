package com.homefit.homefit.report.controller.request;

import com.homefit.homefit.report.domain.BanDuration;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BanRequest {
	@NotNull(message = "정지할 사용자 ID는 필수입니다")
	Long reporteeId;
	@NotNull(message = "정지 기간은 필수입니다")
	BanDuration duration;
	@NotNull(message = "정지 사유는 필수입니다")
	String reason;
	
	
	@Override
	public String toString() {
		return "BanRequest [reporteeId=" + reporteeId + ", duration=" + duration + "]";
	}
}
