package org.example.memberservice.security;

import lombok.extern.slf4j.Slf4j;
import org.example.commonsecurity.jwt.ServiceTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

// 서비스 전용 토큰(feign통신 시) 검사 필터
@Slf4j
public class JwtServiceAuthenticationFilter extends BasicAuthenticationFilter {

    private final ServiceTokenProvider tokenProvider;

    public JwtServiceAuthenticationFilter(ServiceTokenProvider tokenProvider) {
        super(authentication -> authentication); // dummy
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {

        log.info("doFilterInternal 진입 ");

        // 1. http요청 헤더에서 Authorization 값 꺼내기
        String header = request.getHeader("Authorization");

        log.info("Service token filter triggered. Header={}", header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                Claims claims = tokenProvider.validateAndGetClaims(token);
                String subject = claims.getSubject();

                log.info("claims : {}", claims);
                log.info("Subject : {}", subject);

                if (subject != null && subject.startsWith("service:")) {
                    // 서비스 토큰임을 확인 → 인증 성공 처리
                    // 필요하다면 SecurityContextHolder 세팅
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid service token");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}