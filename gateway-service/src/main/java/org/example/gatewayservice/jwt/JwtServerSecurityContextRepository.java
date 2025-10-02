package org.example.gatewayservice.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtServerSecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtAuthenticationManager jwtAuthManager;

    public JwtServerSecurityContextRepository(JwtAuthenticationManager jwtAuthManager) {
        this.jwtAuthManager = jwtAuthManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        // Gateway에서는 보통 SecurityContext 저장하지 않음 (stateless)
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Authentication auth = new UsernamePasswordAuthenticationToken(token, token);

            return this.jwtAuthManager.authenticate(auth)
                    .map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
