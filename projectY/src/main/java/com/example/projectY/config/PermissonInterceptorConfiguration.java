package com.example.projectY.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PermissonInterceptorConfiguration implements WebMvcConfigurer {
    @Bean
    PermissonInterceptor getPermissonInterceptor() {
        return new PermissonInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] whiteList = {
            "/",
            "/api/v1/auth/**",
            "/storage/**",
            "/api/v1/companies",
            "/api/v1/jobs",
            "/api/v1/skills",
            "/api/v1/email"
        };
        registry.addInterceptor(getPermissonInterceptor()).excludePathPatterns(whiteList);
    }

}
