package com.homefit.homefit.notification.controller.response;

import com.homefit.homefit.notification.application.dto.NotificationDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationDetailResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastUpdatedAt;
    private final Long writerId;
    private final String nickname;

    public static NotificationDetailResponse from(NotificationDto dto) {
        return new NotificationDetailResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getCreatedAt(),
                dto.getLastUpdatedAt(),
                dto.getWriterId(),
                dto.getNickname());
    }
}