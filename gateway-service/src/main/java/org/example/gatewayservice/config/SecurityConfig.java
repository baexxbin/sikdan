package org.example.gatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.example.commonsecurity.jwt.JwtTokenProvider;
import org.example.gatewayservice.filter.GatewayJwtFilter;
import org.example.gatewayservice.jwt.JwtAuthenticationManager;
import org.example.gatewayservice.jwt.JwtProperties;
import org.example.gatewayservice.jwt.JwtServerSecurityContextRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;

// GatewayJwtFilter 등록(Bean 생성)

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    private final JwtProperties jwtProperties;

    // JwtTokenProvider Bean 등록
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(jwtProperties.getSecret(), jwtProperties.getExpiration());
    }


    // Gateway 전용 GlobalFilter를 Bean으로 등록 (common-security의 JwtTokenProvider 주입)
    @Bean
    public GatewayJwtFilter gatewayJwtFilter(JwtTokenProvider jwtTokenProvider) {
        return new GatewayJwtFilter(jwtTokenProvider);
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex.anyExchange().permitAll())
                .build();
    }
}