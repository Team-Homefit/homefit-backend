package com.homefit.homefit.board.domain;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.homefit.homefit.exception.HomefitException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    private Long id;
    private Long articleId;
    private Long writerId;
    private Long parentCommentId;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    
    public static Comment of(Long articleid, Long memberId, Long parentCommentId, String content) {
    	return new Comment(null, articleid, memberId, parentCommentId, content, LocalDateTime.now(), false);
    }
    
    public static Comment of(Long commentId, Long articleid, Long memberId, Long parentCommentId, String content, Boolean isDeleted) {
    	return new Comment(commentId, articleid, memberId, parentCommentId, content, LocalDateTime.now(), isDeleted);
    }
    
    public void delete(Long deleterId) {
    	if (this.isDeleted) {
    		throw new HomefitException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다");
    	}
    	
    	if (!this.writerId.equals(deleterId)) {
    		throw new HomefitException(HttpStatus.FORBIDDEN, "댓글 삭제 권한이 없습니다");
    	}
    	
    	this.isDeleted = true;
    }
}
