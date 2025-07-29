package org.example.sikdan;

import org.example.sikdan.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class SikdanApplication {

    public static void main(String[] args) {
        SpringApplication.run(SikdanApplication.class, args);
    }

}
