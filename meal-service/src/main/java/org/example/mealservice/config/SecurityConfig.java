package org.example.mealservice.config;

import lombok.RequiredArgsConstructor;
import org.example.commonsecurity.jwt.JwtAuthenticationFilter;
import org.example.commonsecurity.jwt.JwtServiceAuthenticationFilter;
import org.example.commonsecurity.jwt.JwtTokenProvider;
import org.example.commonsecurity.jwt.ServiceTokenProvider;
import org.example.mealservice.jwt.JwtProperties;
import org.example.mealservice.security.MealCustomUserDetailsFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// common-security모듈을 통해 SecurityAutoConfiguration 에서 공통적으로 필요한 Bean 가져옴
// meal-service의 api정책 정의

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    private final JwtProperties jwtProperties;
    private final MealCustomUserDetailsFactory mealCustomUserDetailsFactory;
    private final ServiceTokenProvider serviceTokenProvider;

    // JwtTokenProvider Bean 등록
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(jwtProperties.getSecret(), jwtProperties.getExpiration());
    }

    // 사용자 토큰 필터 등록
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        // 필터 생성 시, 팩토리를 함께 전달
        return new JwtAuthenticationFilter(jwtTokenProvider, mealCustomUserDetailsFactory);
    }

    // 내부 서비스 통신용 토큰 필터 등록
    @Bean
    public JwtServiceAuthenticationFilter jwtServiceAuthenticationFilter() {
        return new JwtServiceAuthenticationFilter(serviceTokenProvider);
    }


    @Bean
    public SecurityFilterChain mealSecurityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter, JwtServiceAuthenticationFilter jwtServiceAuthenticationFilter) throws Exception {
        http
                .securityMatcher("/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // 인증 없이 허용할 엔드포인트
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtServiceAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
