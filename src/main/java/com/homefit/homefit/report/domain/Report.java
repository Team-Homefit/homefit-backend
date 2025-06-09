package com.homefit.homefit.report.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.homefit.homefit.exception.HomefitException;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Report {
    private Long id;
    private Long reporterId;    // 신고자 ID
    private Long banId;
    private Long targetSourceId;
    private ReportType type;
    private LocalDateTime reportedAt;
    private Boolean isPassed;

    public static Report of(Long reporterId, Long targetSourceId, ReportType type) {
        return new Report(null, reporterId, null, targetSourceId, type, LocalDateTime.now(), false);
    }

    public static Report of(Long id, Long reporterId, Long banId, Long targetSourceId, ReportType type, LocalDateTime reportedAt, Boolean isPassed) {
        return new Report(id, reporterId, banId, targetSourceId, type, reportedAt, isPassed);
    }
    
    public void process(Ban ban) {
    	if (this.banId != null) {
    		throw new HomefitException(HttpStatus.BAD_REQUEST, "이미 처리된 신고입니다");
    	}
    	
    	this.banId = ban.getId();
    }
    
    public void pass() {
    	this.isPassed = true;
    }
}
