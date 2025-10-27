package org.example.gatewayservice.filter;

import lombok.RequiredArgsConstructor;
import org.example.commonsecurity.jwt.JwtTokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GatewayJwtFilter implements GlobalFilter, Ordered {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 외부에서 서비스 코드 조작 못하도록 제거
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest mutatedRequest = request.mutate()
                .headers(httpHeaders -> {
                    httpHeaders.remove("X-Service-Authorization");
                })
                .build();
        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

        String authHeader = mutatedRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 헤더 없으면 그냥 통과(혹은 필요시 차단)
            return chain.filter(mutatedExchange);
        }

        String token = authHeader.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return mutatedExchange.getResponse().setComplete();
        }

        // 유효하면 체인 계속 진행
        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        return -1; // Security 관련 필터 우선순위
    }
}