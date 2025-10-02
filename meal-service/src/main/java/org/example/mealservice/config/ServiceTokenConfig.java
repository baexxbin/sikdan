package org.example.mealservice.config;

import org.example.commonsecurity.jwt.ServiceTokenProvider;
import org.example.mealservice.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// feign통신 시 서비스간의 인증을 위한 토큰 발급 설정

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class ServiceTokenConfig {
    @Bean
    public ServiceTokenProvider serviceTokenProvider(JwtProperties jwtProperties) {
        return new ServiceTokenProvider(jwtProperties.getSecret(), jwtProperties.getExpiration());      // common-security의 ServiceTokenProvider 빈으로 등록
    }
}
