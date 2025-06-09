package com.homefit.homefit.notification.persistence.po;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import com.homefit.homefit.notification.domain.Notification;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationPo {
    private Long notificationId;
    private String notificationTitle;
    private String notificationContent;
    private LocalDateTime notificationCreatedAt;
    private LocalDateTime notificationLastUpdatedAt;
    private Long notificationWriterId;
    private Boolean notificationIsDeleted;
    private String nickname;

    public Notification toDomain() {
        return Notification.of(
                notificationId,
                notificationTitle,
                notificationContent,
                notificationCreatedAt,
                notificationLastUpdatedAt,
                notificationWriterId,
                notificationIsDeleted);
    }
}