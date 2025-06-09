package com.homefit.homefit.notification.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.notification.application.dto.NotificationPageDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationPageResponse {
    private final List<Notification> notifications;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public static NotificationPageResponse from(NotificationPageDto dto) {
        List<Notification> notifications = dto.getNotifications().stream()
                .map(Notification::from)
                .collect(Collectors.toList());
        return new NotificationPageResponse(notifications, dto.getPage(), dto.getSize(), dto.getTotalElements(),
                dto.getTotalPages());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Notification {
        private final Long id;
        private final String title;
        private final LocalDateTime createdAt;

        private static Notification from(NotificationPageDto.Notification dto) {
            return new Notification(
                    dto.getId(),
                    dto.getTitle(),
                    dto.getCreatedAt());
        }
    }
}