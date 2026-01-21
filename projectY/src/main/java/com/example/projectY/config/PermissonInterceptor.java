package com.example.projectY.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.example.projectY.entity.Permission;
import com.example.projectY.entity.Role;
import com.example.projectY.entity.User;
import com.example.projectY.repository.UserRepository;
import com.example.projectY.utils.SecurityUtil;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PermissonInterceptor implements HandlerInterceptor {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        System.out.println("path : "+path);
        System.out.println("methods : "+httpMethod);
        System.out.println("URI : "+requestURI);

        String email = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
        SecurityUtil.getCurrentUserLogin().get() : "";
        if (email != null && !email.isEmpty()) {
            User currentUser = this.userRepository.findByEmail(email).orElse(null);
            if (currentUser != null) {
                Role userRole = currentUser.getRole();
                if (userRole != null) {
                    List<Permission> userPermissions = userRole.getPermissions();
                    Boolean isUserHavePermisson = userPermissions.stream().anyMatch(permisson -> 
                        permisson.getApiPath().equals(requestURI) && permisson.getMethod().equals(httpMethod)
                    );

                    return isUserHavePermisson;
                }
            }
        }
        return false;
    }
}
