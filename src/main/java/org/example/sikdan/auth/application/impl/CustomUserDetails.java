package org.example.sikdan.auth.application.impl;

import lombok.RequiredArgsConstructor;
import org.example.sikdan.domain.member.model.vo.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//  Member객체를 Spring Security에 맞게 변환

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Member member;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberName();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public Long getMemberId() {
        return member.getMemberId();
    }
}
