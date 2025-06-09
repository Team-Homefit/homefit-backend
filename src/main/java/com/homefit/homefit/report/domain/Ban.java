package com.homefit.homefit.report.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.homefit.homefit.exception.HomefitException;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Ban {
    private Long id;
    private Long bannedMemberId;
    private LocalDateTime start;
    private LocalDateTime expire;
    private String reason;
    private Boolean submitApology;

    public static Ban of(Long bannedMemberId, LocalDateTime start, LocalDateTime expire, String reason) {
        return new Ban(null, bannedMemberId, start, expire, reason, false);
    }
    
    public static Ban of(Long id, Long bannedMemberId, LocalDateTime start, LocalDateTime expire, String reason, Boolean submitApology) {
        return new Ban(id, bannedMemberId, start, expire, reason, submitApology);
    }
    
    public void apology(Long memberId) {
    	if (!this.bannedMemberId.equals(memberId)) {
    		throw new HomefitException(HttpStatus.FORBIDDEN, "본인의 정지 건에만 반성문을 제출할 수 있습니다");
    	}
    	
    	this.submitApology = true;
    }

    @Override
    public String toString() {
        return "Ban{" +
                "id=" + id +
                ", bannedMemberId=" + bannedMemberId +
                ", start=" + start +
                ", expire=" + expire +
                ", reason='" + reason + '\'' +
                ", submitApology=" + submitApology +
                '}';
    }
}
