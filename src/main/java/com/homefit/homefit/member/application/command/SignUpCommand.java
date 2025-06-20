package com.homefit.homefit.member.application.command;

import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.member.domain.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpCommand {
    private final String username;
    private final String password;
    private final String nickname;
    private final Gender gender;
    private final String tel;
    private final LocalDate birthday;

    public static SignUpCommand of(String username, String password, String nickname, Gender gender, String tel, LocalDate birthday) {
        if (username == null) {
            throw new HomefitException(HttpStatus.FORBIDDEN, "이메일 인증 후 가입할 수 있습니다");
        }

        return new SignUpCommand(username, password, nickname, gender, tel, birthday);
    }
}
