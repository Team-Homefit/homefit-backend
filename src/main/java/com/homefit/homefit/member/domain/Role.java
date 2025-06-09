package com.homefit.homefit.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"), BASIC("ROLE_BASIC"), BANNED("ROLE_BANNED");

    private final String role;
}
