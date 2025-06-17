package com.homefit.homefit.notification.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateNotificationCommand {
    private final Long id;
    private final String title;
    private final String content;

    public static UpdateNotificationCommand of(Long id, String title, String content) {
        return new UpdateNotificationCommand(id, title, content);
    }
}
