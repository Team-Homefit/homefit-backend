package com.homefit.homefit.notification.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateNotificationCommand {
    private final String title;
    private final String content;

    public static CreateNotificationCommand of(String title, String content) {
        return new CreateNotificationCommand(title, content);
    }
}
