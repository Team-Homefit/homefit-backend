package com.homefit.homefit.member.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRoleResponse {
    private final Long id;
    private final String role;
}
