package org.example.mealservice.security;


import io.jsonwebtoken.Claims;
import org.example.commonsecurity.auth.CustomUserDetails;
import org.example.commonsecurity.auth.CustomUserDetailsFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MealCustomUserDetailsFactory implements CustomUserDetailsFactory {

    @Override
    public Authentication createAuthentication(Claims claims) {
        Long memberId = ((Number) claims.get("memberId")).longValue();
        String email = claims.getSubject();

        CustomUserDetails userDetails = new CustomUserDetails(memberId, email, List.of(new SimpleGrantedAuthority("ROLE_USER")));

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
