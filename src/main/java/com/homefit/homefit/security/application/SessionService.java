package com.homefit.homefit.security.application;

import java.util.List;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import com.homefit.homefit.security.application.dto.MemberDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SessionService {
    private final SessionRegistry sessionRegistry;

    public void signOut(Long targetId) {
        List<Object> principals = sessionRegistry.getAllPrincipals();

        for (Object principal : principals) {
            if (principal instanceof MemberDetails memberDetails) {
                if (!memberDetails.getId().equals(targetId)) {
                    continue;
                }

                List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
                for (SessionInformation sessionInfo : sessions) {
                    sessionInfo.expireNow();
                }
            }
        }
    }
}
