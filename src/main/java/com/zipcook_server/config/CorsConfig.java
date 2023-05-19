package com.zipcook_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // /auth/ 경로에 대한 CORS 정책 등록
        source.registerCorsConfiguration("/auth/**", config);

        // /chatbot/ 경로에 대한 CORS 정책 등록
        source.registerCorsConfiguration("/chatbot/**", config);

        return new CorsFilter(source);
    }

}