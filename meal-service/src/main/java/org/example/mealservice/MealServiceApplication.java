package org.example.mealservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "org.example.mealservice.infrastructure.client")
@EnableDiscoveryClient
public class MealServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealServiceApplication.class, args);
	}

}
