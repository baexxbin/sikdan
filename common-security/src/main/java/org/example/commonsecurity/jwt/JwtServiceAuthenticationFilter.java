package org.example.commonsecurity.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// 서비스 전용 토큰(feign통신 시) 검사 필터
@Slf4j
@RequiredArgsConstructor
public class JwtServiceAuthenticationFilter extends OncePerRequestFilter {

    private final ServiceTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {

        log.info("doFilterInternal 진입 ");

        // 1. 서비스 토큰 여부 검사
        String serviceHeader = request.getHeader("X-Service-Authorization");
        if (serviceHeader == null) {        // 없을 경우 다음 필터 진행
            chain.doFilter(request, response);
            return;
        }

        if (!serviceHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = serviceHeader.substring(7);
        try {
            Claims claims = tokenProvider.validateAndGetClaims(token);
            String subject = claims.getSubject();
            if (subject != null && subject.startsWith("service:")) {
                String role = claims.get("role", String.class);
                if (role == null) role = "ROLE_SERVICE";

                Authentication auth = new UsernamePasswordAuthenticationToken(
                        subject, null, List.of(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(auth);

                log.info("Service authenticated successfully with subject={}", subject);
            }
            chain.doFilter(request, response);

        } catch (Exception e) {
            log.error("Service Token 검증 실패", e);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid service token");
        }
    }
}