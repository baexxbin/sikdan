package org.example.commonsecurity.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Long memberId;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    // 비밀번호는 JWT 기반 인증에서는 필요 없으므로 null 반환
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email; // UserDetails에서 사용자명은 이메일로 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
