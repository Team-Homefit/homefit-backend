package com.homefit.homefit.security.application.dto;

import com.homefit.homefit.member.domain.Role;
import com.homefit.homefit.member.persistence.po.MemberPo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDetails implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final Role role;

    public static MemberDetails from(MemberPo member) {
        return new MemberDetails(
                member.getMemberId(),
                member.getUsername(),
                member.getPassword(),
                member.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authorize = new SimpleGrantedAuthority(role.getRole());

        return List.of(authorize);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Long getId() {
        return this.id;
    }
    
    public Role getRole() {
    	return this.role;
    }
}
