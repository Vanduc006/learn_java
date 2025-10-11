package com.example.projectY.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // ❌ tắt CSRF khi dev/test API
            .csrf(csrf -> csrf.disable())
            // ✅ cho phép tất cả request, không cần login
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            // ❌ tắt form login mặc định
            .formLogin(form -> form.disable())
            // ❌ tắt basic auth (popup login)
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
