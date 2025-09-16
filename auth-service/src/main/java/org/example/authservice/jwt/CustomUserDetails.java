package org.example.authservice.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

//  Member객체를 Spring Security에 맞게 변환
// 토큰 검증 시 SecurityContext에 유저 저장
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Long memberId;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // JWT 기반이라 비밀번호 필요 없음
    }

    @Override
    public String getUsername() {
        return email; // email을 username으로 사용
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public Long getMemberId() {
        return memberId;
    }
}
