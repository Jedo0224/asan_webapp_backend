package com.asanhospital.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import java.util.TimeZone;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@OpenAPIDefinition
@EnableMongoRepositories(basePackages = "com.asanhospital.server")
public class ServerApplication {

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
