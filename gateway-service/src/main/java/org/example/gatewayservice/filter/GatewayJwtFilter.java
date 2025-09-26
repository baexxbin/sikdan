package org.example.gatewayservice.filter;

import lombok.RequiredArgsConstructor;
import org.example.commonsecurity.jwt.JwtTokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GatewayJwtFilter implements GlobalFilter, Ordered {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 로그인/회원가입은 토큰 검사하지 않고 바로 통과
        String path = exchange.getRequest().getPath().toString();
        if (path.startsWith("/api/auth/")) {
            return chain.filter(exchange);
        }

        String token = extractToken(exchange);

        if (token == null || !jwtTokenProvider.validateToken(token)) {
            // 토큰이 없거나 잘못된 경우 → 401 응답
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 유효하면 체인 계속 진행
        return chain.filter(exchange);
    }

    private String extractToken(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String bearerToken = headers.getFirst(HttpHeaders.AUTHORIZATION);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return -1; // Security 관련 필터 우선순위
    }
}