package com.homefit.homefit.notification.application.dto;

import com.homefit.homefit.notification.persistence.po.NotificationPo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastUpdatedAt;
    private final Long writerId;
    private final Boolean isDeleted;
    private final String nickname;

    public static NotificationDto from(NotificationPo notificationPo) {
        return new NotificationDto(
                notificationPo.getNotificationId(),
                notificationPo.getNotificationTitle(),
                notificationPo.getNotificationContent(),
                notificationPo.getNotificationCreatedAt(),
                notificationPo.getNotificationLastUpdatedAt(),
                notificationPo.getNotificationWriterId(),
                notificationPo.getNotificationIsDeleted(),
                notificationPo.getNickname());
    }
}