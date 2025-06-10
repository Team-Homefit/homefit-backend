package com.homefit.homefit.member.application.command;

import com.homefit.homefit.exception.HomefitException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyPasswordCommand {
    private final String username;
    private final String newPassword;

    public static ModifyPasswordCommand of(String username, String newPassword) {
        if (username == null) {
            throw new HomefitException(HttpStatus.FORBIDDEN, "이메일 인증 후 변경할 수 있습니다");
        }

        return new ModifyPasswordCommand(username, newPassword);
    }
}
