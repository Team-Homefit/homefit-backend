package com.homefit.homefit.member.application.dto;

import com.homefit.homefit.member.domain.Gender;
import com.homefit.homefit.member.domain.Role;
import com.homefit.homefit.member.persistence.po.MemberPo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberDto {
    private final Long id;
    private final String username;
    private final String nickname;
    private final String tel;
    private final Gender gender;
    private final LocalDate birthday;
    private final LocalDateTime signUpAt;
    private final Role role;

    @Override
    public String toString() {
        return "MemberDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", tel='" + tel + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", signUpAt=" + signUpAt +
                ", role=" + role +
                '}';
    }

    public static MemberDto from(MemberPo member) {
        return new MemberDto(
                member.getMemberId(),
                member.getUsername(),
                member.getNickname(),
                member.getTel(),
                member.getGender(),
                member.getBirthDate(),
                member.getMemberSignupAt(),
                member.getRole()
        );
    }

    public static MemberDto fromWithMask(MemberPo member) {
        return new MemberDto(
                member.getMemberId(),
                member.getUsername().substring(0, 2) + "*****",
                member.getNickname(),
                null,
                null,
                null,
                null,
                null
        );
    }
}
