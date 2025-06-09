package com.homefit.homefit.member.controller.request;

import com.homefit.homefit.member.domain.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyRoleRequest {
    @NotNull(message = "사용자 ID는 필수입니다")
    private final Long id;
    @NotNull(message = "변경될 권한은 필수입니다")
    private final Role role;

    @Override
    public String toString() {
        return "ModifyRoleRequest{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }

    public static ModifyRoleRequest of(Long id, Role role) {
        return new ModifyRoleRequest(id, role);
    }
}
