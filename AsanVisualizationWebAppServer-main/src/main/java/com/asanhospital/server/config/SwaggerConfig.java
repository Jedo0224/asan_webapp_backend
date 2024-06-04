package com.asanhospital.server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("아산병원 시각화 웹앱 API")
                .version("v0.0.1")
                .description("아산병원 시각화 웹앱의 API 명세서입니다.");
        return new OpenAPI()
                .info(info);
    }

}