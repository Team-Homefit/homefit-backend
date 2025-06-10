package com.homefit.homefit.auth.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VerifyCodeCommand {
    private String username;
    private String code;

    public static VerifyCodeCommand of(String username, String code) {
        return new VerifyCodeCommand(username, code);
    }
}
