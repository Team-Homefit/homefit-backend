package com.homefit.homefit.notification.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.homefit.homefit.notification.persistence.po.NotificationPo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationPageDto {
    private final List<Notification> notifications;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public static NotificationPageDto of(List<Notification> notifications, int page, int size,
            long totalElements,
            int totalPages) {
        return new NotificationPageDto(notifications, page, size, totalElements, totalPages);
    }

    // 공지사항 목록 조회 시 사용
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Notification {
        private final Long id;
        private final String title;
        private final LocalDateTime createdAt;

        public static Notification from(NotificationPo po) {
            return new Notification(
                    po.getNotificationId(),
                    po.getNotificationTitle(),
                    po.getNotificationCreatedAt());
        }
    }
}
