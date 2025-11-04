package com.example.projectY.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.projectY.response.ApiResponeDTO;
import com.example.projectY.response.ResponeStatusDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{
    // @Autowired
    private ObjectMapper mapper;

    // @Autowired
    private AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();
    
    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        this.delegate.commence(request, response, authException);
        response.setContentType("application/json;charset:UTF-8");
        // RestRespone
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.UNAUTHORIZED,"Invalid access" );
        ApiResponeDTO apiRespone = new ApiResponeDTO<>(status, null, LocalDateTime.now());
        mapper.writeValue(response.getWriter(), apiRespone);

    }

    
}
