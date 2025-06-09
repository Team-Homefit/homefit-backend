package com.homefit.homefit.member.persistence.po;

import com.homefit.homefit.member.domain.Gender;
import com.homefit.homefit.member.domain.Member;
import com.homefit.homefit.member.domain.Role;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class MemberPo {
    private Long memberId;
    private String username;
    private String password;
    private String nickname;
    private String tel;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDateTime memberSignupAt;
    private Boolean memberIsDeleted;
    private Role role;
    
    public Member toDomain() {
        return Member.of(memberId, username, password, nickname, gender, tel, birthDate, memberSignupAt, role, memberIsDeleted);
    }
}
