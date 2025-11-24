package com.example.projectY.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;


@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer{
    @Value("${projectY.upload.base-URI}")
    private String baseURI;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/v1/storage/**")
        .addResourceLocations(baseURI);
    }
}
