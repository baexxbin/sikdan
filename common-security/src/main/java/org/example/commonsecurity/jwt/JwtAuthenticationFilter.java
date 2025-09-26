package org.example.commonsecurity.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commonsecurity.auth.CustomUserDetailsFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    // 서비스별 CustomUserDetailsFactory (직접 구현)
    private final CustomUserDetailsFactory customUserDetailsFactory;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. 헤더에서 토큰 추출
        String token = extractToken(request);

        // 2. 토큰 유효성 검사
        if (token != null) {
            if (jwtTokenProvider.validateToken(token)) {
                // 팩토리를 통해 Authentication 생성 (서비스별 CustomUserDetails로 변환)
                Authentication authentication = customUserDetailsFactory.createAuthentication(jwtTokenProvider.getClaims(token));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.debug("유효하지 않은 토큰");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;     // 더 이상 필터체인을 타지 않도록 막음
            }
        }

        // 4. 다음 필터로 전달
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
