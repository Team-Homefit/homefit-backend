package com.homefit.homefit.board.domain;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.homefit.homefit.exception.HomefitException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Article {
    private Long id;
    private Long writerId;
    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private boolean isDeleted;

    public static Article of(Long writerId, Long boardId, String title, String content) {
        return new Article(null, writerId, boardId, title, content, LocalDateTime.now(), null, false);
    }

    public static Article of(Long id, Long writerId, Long boardId, String title, String content,
            LocalDateTime createdAt, LocalDateTime lastUpdatedAt, boolean isDeleted) {
        return new Article(id, writerId, boardId, title, content, createdAt, LocalDateTime.now(), isDeleted);
    }

    public void update(String title, String content, Long updaterId) {
        if (this.isDeleted) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다");
        }

        if (!this.writerId.equals(updaterId)) {
            throw new HomefitException(HttpStatus.FORBIDDEN, "게시글 수정 권한이 없습니다");
        }

        this.title = title;
        this.content = content;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public void delete(Long deleterId) {
        if (this.isDeleted) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다");
        }

        if (!this.writerId.equals(deleterId)) {
            throw new HomefitException(HttpStatus.FORBIDDEN, "게시글 삭제 권한이 없습니다");
        }

        this.isDeleted = true;
    }
}