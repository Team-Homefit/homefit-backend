package com.homefit.homefit.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.homefit.homefit.exception.HomefitException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member {
    private Long id;
    private String username;
    private String encodedPassword;
    private String nickname;
    private Gender gender;
    private String tel;
    private LocalDate birthday;
    private LocalDateTime signUpAt;
    private Role role;
    private Boolean isDelete;

    public static Member of(String username, String encodedPassword, String nickname, Gender gender, String tel, LocalDate birthday, LocalDateTime signUpAt, Role role) {
        return new Member(
                null, username, encodedPassword, nickname, gender, tel, birthday, signUpAt, role, false
        );
    }

    public static Member of(Long id, String username, String encodedPassword, String nickname, Gender gender, String tel, LocalDate birthday, LocalDateTime signUpAt, Role role, Boolean isDelete) {
        return new Member(
                id, username, encodedPassword, nickname, gender, tel, birthday, signUpAt, role, isDelete
        );
    }
    
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public void updateGender(Gender gender) {
        this.gender = gender;
    }
    
    public void updateTel(String tel) {
        this.tel = tel;
    }
    
    public void updateBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    
    public void updatePassword(String newPassword) {        
        this.encodedPassword = newPassword;
    }
    
    public void updateRole(Role role) {
        if (role.equals(Role.ADMIN)) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "관리자로의 권한 변경을 불가능합니다");
        }
        
        this.role = role;
    }
    
    public void delete() {
        this.isDelete = true;
    }
}
