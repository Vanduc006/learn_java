package com.example.projectY.controller;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectY.entity.User;
import com.example.projectY.request.ReqLoginDTO;
import com.example.projectY.response.ApiResponeDTO;
import com.example.projectY.response.ResLoginDTO;
import com.example.projectY.response.ResponeStatusDTO;
import com.example.projectY.response.ResLoginDTO.GetUserDTO;
import com.example.projectY.response.ResLoginDTO.UserLoginDTO;
import com.example.projectY.service.UserService;
import com.example.projectY.utils.SecurityUtil;

import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private UserService userService;

    @Value("${projectY.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpriration;

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponeDTO<ResLoginDTO>> login(
        @Valid
        @RequestBody ReqLoginDTO loginDTO
    ) {
        // Put data into Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        // verify in loadUserByUsername in UserDetailsImpl
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // Create token
        
        User currentUser = this.userService.getUserByEmail(loginDTO.getUsername());
        ResLoginDTO.UserLoginDTO userLoginDTO = new ResLoginDTO.UserLoginDTO();
        BeanUtils.copyProperties(currentUser, userLoginDTO);

        String accessToken = this.securityUtil.createAccessToken(authentication.getName(), userLoginDTO);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.OK, "User login");

        // create refresh token
        String refresh_token = this.securityUtil.createRefreshToken(loginDTO.getUsername(), new ResLoginDTO(accessToken, userLoginDTO));

        // update
        this.userService.updateUserToken(refresh_token, loginDTO.getUsername());

        // Cookies
        ResponseCookie responseCookie = ResponseCookie
        .from("refresh_token",refresh_token)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(refreshTokenExpriration)
        .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString() ).body(new ApiResponeDTO<ResLoginDTO>(status, new ResLoginDTO(accessToken, userLoginDTO), LocalDateTime.now()));
        
    }

    @GetMapping("/auth/logout")
    public ResponseEntity<ApiResponeDTO<?>> logout(
        @CookieValue(name = "refresh_token", defaultValue = "refresh_token") String refreshToken
    ) {
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.OK, "User logout");
        // check valid
        Jwt decodeToken = this.securityUtil.validRefreshToken(refreshToken);
        // Remove refresh token
        this.userService.removeRefreshToken(decodeToken.getSubject());
        // Replace cookies
        ResponseCookie deleteToken = ResponseCookie
        .from("refresh_token",null)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(0)
        .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, deleteToken.toString()).body(new ApiResponeDTO<>(status, null, LocalDateTime.now()));
    }

    @GetMapping("/auth/account")
    public ResponseEntity<ApiResponeDTO<?>> getAccount() {
        System.out.println("?");
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? 
        SecurityUtil.getCurrentUserLogin().get() : "";

        System.out.println(email);

        User currentUser = this.userService.getUserByEmail(email);
        ResLoginDTO.UserLoginDTO userLoginDTO = new UserLoginDTO();
        BeanUtils.copyProperties(currentUser, userLoginDTO);
        
        ResLoginDTO.GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUser(userLoginDTO);

        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.OK, "Get auth user");

        return ResponseEntity.ok().body(new ApiResponeDTO<>(status, getUserDTO, LocalDateTime.now()));
        
    }

    @GetMapping("/auth/refresh")
    public ResponseEntity<ApiResponeDTO<?>> getFreshToken(
        @CookieValue(name="refresh_token", defaultValue = "refresh_token") String refresh_token
    ) {
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.OK, "Get refresh token");
        // check valid
        Jwt decodeToken = this.securityUtil.validRefreshToken(refresh_token);
        // if (!this.userService.validUserRefreshTokenAndEmail(refresh_token, decodeToken.getSubject())) {
        //     throw new NoSuchElementException("Refresh token or email not valid");
        // }
        // Get user
        ResLoginDTO.UserLoginDTO userLoginDTO = new UserLoginDTO();
        BeanUtils.copyProperties(this.userService.getUserByEmail(decodeToken.getSubject()),userLoginDTO);

        String accessToken = this.securityUtil.createAccessToken(decodeToken.getSubject(), userLoginDTO);

        // create refresh token
        String newRefreshToken = this.securityUtil.createRefreshToken(decodeToken.getSubject(), new ResLoginDTO(accessToken, userLoginDTO));

        // update
        this.userService.updateUserToken(newRefreshToken, decodeToken.getSubject());

        // Set cookies
        ResponseCookie responseCookie = ResponseCookie
        .from("refresh_token",newRefreshToken)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(refreshTokenExpriration)
        .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(new ApiResponeDTO<>(status, userLoginDTO, LocalDateTime.now()));
    }
}
