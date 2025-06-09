package com.homefit.homefit.security.handler;

import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.member.domain.Role;
import com.homefit.homefit.security.util.UserPrincipalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Long memberId = UserPrincipalUtil.getId()
                        .orElseThrow(() -> new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "인증 정보 조회에 실패했습니다"));
        Role role = UserPrincipalUtil.getRole()
                .orElseThrow(() -> new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "인증 정보 조회에 실패했습니다"));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        String json = "{\"memberId\":\"" + memberId + "\", \"role\":\"" + role.name() + "\"}";
        response.getWriter().write(json);
    }
}
