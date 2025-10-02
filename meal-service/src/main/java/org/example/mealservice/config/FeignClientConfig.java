package org.example.mealservice.config;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.example.commonsecurity.jwt.ServiceTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// feign통신시 서비스 토큰 붙이기

@Configuration
@Slf4j
public class FeignClientConfig {

    private final ServiceTokenProvider tokenProvider;

    public FeignClientConfig(ServiceTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public RequestInterceptor serviceAuthInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String token = tokenProvider.generateServiceToken("meal-service");
                template.header("Authorization", "Bearer " + token);

                log.info("MealService 서비스 토큰 생성");
                log.info("Generated service token: {}", token);
                log.info("Feign request to {} with headers: {}", template.url(), template.headers());
            }
        };
    }
}