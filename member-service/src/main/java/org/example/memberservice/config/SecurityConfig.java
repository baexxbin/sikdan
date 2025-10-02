package org.example.memberservice.config;


import lombok.RequiredArgsConstructor;
import org.example.commonsecurity.jwt.JwtAuthenticationFilter;
import org.example.commonsecurity.jwt.JwtTokenProvider;
import org.example.commonsecurity.jwt.ServiceTokenProvider;
import org.example.memberservice.jwt.JwtProperties;
import org.example.memberservice.security.JwtServiceAuthenticationFilter;
import org.example.memberservice.security.MemberCustomUserDetailsFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    private final JwtProperties jwtProperties;
    private final MemberCustomUserDetailsFactory memberCustomUserDetailsFactory;

    // JwtTokenProvider Bean 등록
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(jwtProperties.getSecret(), jwtProperties.getExpiration());
    }

    // factory를 주입해서 필터를 Bean으로 생성
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        // 필터 생성 시, 팩토리를 함께 전달
        return new JwtAuthenticationFilter(jwtTokenProvider, memberCustomUserDetailsFactory);
    }

    // 내부 서비스 발급토큰 필터도 Bean 등록
    @Bean
    public JwtServiceAuthenticationFilter jwtServiceAuthenticationFilter(ServiceTokenProvider serviceTokenProvider) {
        return new JwtServiceAuthenticationFilter(serviceTokenProvider);
    }

    @Bean
    public SecurityFilterChain memberSecurityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtServiceAuthenticationFilter jwtServiceAuthenticationFilter
    ) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/member/register", "/api/member/email/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 서비스 토큰 필터 먼저 → 사용자 토큰 필터 다음
                .addFilterBefore(jwtServiceAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
