package com.homefit.homefit.member.controller.request;

import com.homefit.homefit.member.domain.Gender;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyMemberRequest {
    @Pattern(
            regexp = "^[A-Za-z0-9가-힣ㄱ-ㅎㅏ-ㅣ!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{3,16}$",
            message = "닉네임은 한글, 영문, 숫자, 특수문자 조합의 3-16자여야 합니다"
    )
    private final String nickname;
    private final Gender gender;
    @Pattern(
            regexp = "^0\\d{2}-\\d{3,4}-\\d{4}",
            message = "전화번호는 0XX-XXX-XXXX 혹은 0XX-XXXX-XXXX 양식이어야 합니다"
    )
    private final String tel;
    private final LocalDate birthday;

    @Override
    public String toString() {
        return "ModifyMemberRequest{" +
                "nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", tel='" + tel + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
