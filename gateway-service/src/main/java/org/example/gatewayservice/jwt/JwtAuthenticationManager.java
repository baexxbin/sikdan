package org.example.gatewayservice.jwt;

import io.jsonwebtoken.Claims;
import org.example.commonsecurity.jwt.JwtTokenProvider;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationManager(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        if (jwtTokenProvider.validateToken(token)) {
            Claims claims = jwtTokenProvider.getClaims(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), null, Collections.emptyList()
            );
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}
