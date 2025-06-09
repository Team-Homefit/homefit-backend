package com.homefit.homefit.member.controller.response;

import com.homefit.homefit.member.application.dto.MemberDto;
import com.homefit.homefit.member.domain.Gender;
import com.homefit.homefit.member.domain.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {
    private final Long id;
    private final String username;
    private final String nickname;
    private final String tel;
    private final Gender gender;
    private final LocalDate birthday;
    private final LocalDateTime signUpAt;
    private final Role role;

    public static MemberResponse fromMember(MemberDto memberDto) {
        return new MemberResponse(
                memberDto.getId(),
                memberDto.getUsername(),
                memberDto.getNickname(),
                memberDto.getTel(),
                memberDto.getGender(),
                memberDto.getBirthday(),
                memberDto.getSignUpAt(),
                memberDto.getRole()
        );
    }
}
