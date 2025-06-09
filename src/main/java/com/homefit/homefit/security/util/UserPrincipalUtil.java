package com.homefit.homefit.security.util;

import com.homefit.homefit.member.domain.Role;
import com.homefit.homefit.security.application.dto.MemberDetails;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserPrincipalUtil {
    public static Optional<Long> getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object userDetails = authentication.getPrincipal();

        if (userDetails instanceof MemberDetails memberDetails) {
            return Optional.of(memberDetails.getId());
        }

        return Optional.empty();
    }
    
    public static Optional<Role> getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object userDetails = authentication.getPrincipal();

        if (userDetails instanceof MemberDetails memberDetails) {
            return Optional.of(memberDetails.getRole());
        }
        return Optional.empty();
    }
}
