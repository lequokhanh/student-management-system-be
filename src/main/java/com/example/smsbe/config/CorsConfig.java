package com.example.smsbe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:*/*", "http://127.0.0.1:*")
                .allowedOrigins("http://localhost:3000","http://localhost:3000/",
                        "https://disciplinary-arlen-lequo-fbc0dc5c.koyeb.app/",
                        "http://disciplinary-arlen-lequo-fbc0dc5c.koyeb.app/")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}