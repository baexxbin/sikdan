package org.example.memberservice.security;


import io.jsonwebtoken.Claims;
import org.example.commonsecurity.auth.CustomUserDetails;
import org.example.commonsecurity.auth.CustomUserDetailsFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MemberCustomUserDetailsFactory implements CustomUserDetailsFactory {

    // roles, 상태 등을 포함해 Authentication 구성
    @Override
    public Authentication createAuthentication(Claims claims) {
        Long memberId = claims.get("memberId", Long.class);
        String email = claims.getSubject();

        List<GrantedAuthority> authorities = extractAuthoritiesFromClaims(claims);

        CustomUserDetails userDetails = new CustomUserDetails(memberId, email, authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private List<GrantedAuthority> extractAuthoritiesFromClaims(Claims claims) {
        Object rolesObj = claims.get("roles");

        if (rolesObj instanceof List<?>) {
            List<?> raw = (List<?>) rolesObj;
            return raw.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
        }

        if (rolesObj instanceof String) {
            String s = ((String) rolesObj).trim();
            if (s.isEmpty()) {
                return List.of(new SimpleGrantedAuthority("ROLE_USER"));
            }
            return Arrays.stream(s.split(","))
                    .map(String::trim)
                    .filter(r -> !r.isEmpty())
                    .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
        }

        // 기본 ROLE_USER 반환
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
