package com.homefit.homefit.member.application.command;

import com.homefit.homefit.member.domain.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyMemberCommand {
    private final String nickname;
    private final Gender gender;
    private final String tel;
    private final LocalDate birthday;

    public static ModifyMemberCommand of(String nickname, Gender gender, String tel, LocalDate birthday) {
        return new ModifyMemberCommand(nickname, gender, tel, birthday);
    }
}
