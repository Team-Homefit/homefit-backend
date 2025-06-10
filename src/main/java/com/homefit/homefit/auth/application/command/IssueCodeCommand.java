package com.homefit.homefit.auth.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IssueCodeCommand {
    private String username;
    private Boolean isSignUp;

    public static IssueCodeCommand of(String username, Boolean isSignUp) {
        return new IssueCodeCommand(username, isSignUp);
    }
}
