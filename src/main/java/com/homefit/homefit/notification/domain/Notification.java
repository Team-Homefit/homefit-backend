package com.homefit.homefit.notification.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Notification {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private Long writerId;
    private Boolean isDeleted;

    public static Notification of(Long id, String title, String content, LocalDateTime createdAt,
            LocalDateTime lastUpdatedAt, Long writerId, Boolean isDeleted) {
        return new Notification(
                id,
                title,
                content,
                createdAt,
                lastUpdatedAt,
                writerId,
                isDeleted);
    }

    // 새로운 공지사항 생성 시 사용
    public static Notification of(String title, String content, Long writerId) {
        return new Notification(
                null,
                title,
                content,
                LocalDateTime.now(),
                null,
                writerId,
                false // isDeleted
        );
    }

    // 기존 공지사항 수정 시 사용
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.lastUpdatedAt = LocalDateTime.now();
    }
}