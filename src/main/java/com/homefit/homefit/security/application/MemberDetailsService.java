package com.homefit.homefit.security.application;

import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.member.persistence.MemberRepository;
import com.homefit.homefit.member.persistence.po.MemberPo;
import com.homefit.homefit.security.application.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberPo member = memberRepository.selectByUsername(username);

        if (member == null) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다.");
        }

        MemberDetails memberDetails = MemberDetails.from(member);

        return memberDetails;
    }
}
