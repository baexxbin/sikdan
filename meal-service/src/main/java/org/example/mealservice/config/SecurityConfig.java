package org.example.mealservice.config;

import lombok.RequiredArgsConstructor;
import org.example.commonsecurity.jwt.JwtAuthenticationFilter;
import org.example.commonsecurity.jwt.JwtTokenProvider;
import org.example.mealservice.jwt.JwtProperties;
import org.example.mealservice.security.MealCustomUserDetailsFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

    // JwtTokenProvider Bean 등록
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(jwtProperties.getSecret(), jwtProperties.getExpiration());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        // 필터 생성 시, 팩토리를 함께 전달
        return new JwtAuthenticationFilter(jwtTokenProvider, mealCustomUserDetailsFactory);
    }


    @Bean
    public SecurityFilterChain mealSecurityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // 필요시 서비스별로 설정
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // 인증 없이 허용할 엔드포인트
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
