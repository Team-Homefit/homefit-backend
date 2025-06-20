package com.homefit.homefit.member.application.command;

import com.homefit.homefit.member.domain.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyRoleCommand {
    private final Long id;
    private final Role role;

    public static ModifyRoleCommand of(Long id, Role role) {
        return new ModifyRoleCommand(id, role);
    }
}
