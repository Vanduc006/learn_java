package com.example.projectY.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectY.entity.User;
import com.example.projectY.request.ReqLoginDTO;
import com.example.projectY.response.ApiResponseDTO;
import com.example.projectY.response.ResLoginDTO;
import com.example.projectY.response.ResponseStatusDTO;
import com.example.projectY.response.user.ResCreateUserDTO;
import com.example.projectY.response.ResLoginDTO.GetUserDTO;
import com.example.projectY.response.ResLoginDTO.UserInsideToken;
import com.example.projectY.response.ResLoginDTO.UserLoginDTO;
import com.example.projectY.response.ResLoginDTO.UserLoginDTO.RoleUserLoginDTO;
import com.example.projectY.response.ResLoginDTO.UserLoginDTO.RoleUserLoginDTO.PermissionRoleUserLoginDTO;
import com.example.projectY.service.UserService;
import com.example.projectY.utils.SecurityUtil;

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

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponseDTO<?>> login(
        @Valid
        @RequestBody ReqLoginDTO loginDTO
    ) {
        // Put data into Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        // verify in loadUserByUsername in UserDetailsImpl
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // Create token
        
        // User entity
        User currentUser = this.userService.getUserByEmail(loginDTO.getUsername());
        // Permission login DTO
        List<ResLoginDTO.UserLoginDTO.RoleUserLoginDTO.PermissionRoleUserLoginDTO> permissions = new ArrayList<ResLoginDTO.UserLoginDTO.RoleUserLoginDTO.PermissionRoleUserLoginDTO>();
        if (currentUser.getRole() != null) {
            if (currentUser.getRole().getPermissions() != null) {
                permissions = currentUser.getRole().getPermissions()
                .stream().map(permisson -> {
                    ResLoginDTO.UserLoginDTO.RoleUserLoginDTO.PermissionRoleUserLoginDTO permissonLoginDTO = new PermissionRoleUserLoginDTO();
                    BeanUtils.copyProperties(permisson, permissonLoginDTO);
                    return permissonLoginDTO;
                }).toList();
            }
        }


        // Role login DTO
        ResLoginDTO.UserLoginDTO.RoleUserLoginDTO roleUserLoginDTO = new RoleUserLoginDTO();
        if (currentUser.getRole() != null) {
            BeanUtils.copyProperties(currentUser.getRole(), roleUserLoginDTO);
            roleUserLoginDTO.setPermissions(permissions);
        }

        ResLoginDTO.UserLoginDTO userLoginDTO = new ResLoginDTO.UserLoginDTO();
        // if (currentUser.getRole() != null) {
        BeanUtils.copyProperties(currentUser, userLoginDTO);
        userLoginDTO.setRole(roleUserLoginDTO);
            // userLoginDTO.setRole(currentUser.getRole());
        // }

        // currentUser.getRole().getPermissions()

        ResLoginDTO.UserInsideToken userInsideToken = new UserInsideToken();
        BeanUtils.copyProperties(userLoginDTO, userInsideToken);

        String accessToken = this.securityUtil.createAccessToken(authentication.getName(), userLoginDTO);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.OK, "User login");

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

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString() )
        .body(new ApiResponseDTO<>(status, new ResLoginDTO(accessToken, userLoginDTO), null));
        
    }

    @GetMapping("/auth/logout")
    public ResponseEntity<ApiResponseDTO<?>> logout(
        @CookieValue(name = "refresh_token", defaultValue = "refresh_token") String refreshToken
    ) {
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.OK, "User logout");
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
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, deleteToken.toString()).body(new ApiResponseDTO<>(status, null, LocalDateTime.now()));
    }

    @GetMapping("/auth/account")
    public ResponseEntity<ApiResponseDTO<?>> getAccount() {
        System.out.println("?");
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? 
        SecurityUtil.getCurrentUserLogin().get() : "";

        System.out.println(email);

        User currentUser = this.userService.getUserByEmail(email);
        
        // Permission login DTO
        List<ResLoginDTO.UserLoginDTO.RoleUserLoginDTO.PermissionRoleUserLoginDTO> permissions = new ArrayList<ResLoginDTO.UserLoginDTO.RoleUserLoginDTO.PermissionRoleUserLoginDTO>();
        if (currentUser.getRole().getPermissions() != null) {
            permissions = currentUser.getRole().getPermissions()
            .stream().map(permisson -> {
                ResLoginDTO.UserLoginDTO.RoleUserLoginDTO.PermissionRoleUserLoginDTO permissonLoginDTO = new PermissionRoleUserLoginDTO();
                BeanUtils.copyProperties(permisson, permissonLoginDTO);
                return permissonLoginDTO;
            }).toList();
        }

        // Role login DTO
        ResLoginDTO.UserLoginDTO.RoleUserLoginDTO roleUserLoginDTO = new RoleUserLoginDTO();
        if (currentUser.getRole() != null) {
            BeanUtils.copyProperties(currentUser.getRole(), roleUserLoginDTO);
            roleUserLoginDTO.setPermissions(permissions);
        }

        ResLoginDTO.UserLoginDTO userLoginDTO = new ResLoginDTO.UserLoginDTO();
        if (currentUser.getRole() != null) {
            BeanUtils.copyProperties(currentUser, userLoginDTO);
            userLoginDTO.setRole(roleUserLoginDTO);
            // userLoginDTO.setRole(currentUser.getRole());
        }
        
        ResLoginDTO.GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUser(userLoginDTO);


        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.OK, "Get auth user");

        return ResponseEntity.ok().body(new ApiResponseDTO<>(status, getUserDTO, LocalDateTime.now()));
        
    }

    @GetMapping("/auth/refresh")
    public ResponseEntity<ApiResponseDTO<?>> getFreshToken(
        @CookieValue(name="refresh_token", defaultValue = "refresh_token") String refresh_token
    ) {
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.OK, "Get refresh token");
        // check valid
        Jwt decodeToken = this.securityUtil.validRefreshToken(refresh_token);
        // if (!this.userService.validUserRefreshTokenAndEmail(refresh_token, decodeToken.getSubject())) {
        //     throw new NoSuchElementException("Refresh token or email not valid");
        // }
        // Get user
        ResLoginDTO.UserLoginDTO userLoginDTO = new UserLoginDTO();
        BeanUtils.copyProperties(this.userService.getUserByEmail(decodeToken.getSubject()),userLoginDTO);

        ResLoginDTO.UserInsideToken userInsideToken = new UserInsideToken();
        BeanUtils.copyProperties(userLoginDTO, userInsideToken);
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

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(new ApiResponseDTO<>(status, userLoginDTO, LocalDateTime.now()));
    }

    @GetMapping("/auth/register")
    public ResponseEntity<ApiResponseDTO<?>> register(
        @Valid @RequestBody User newUser
    ) {
        String hashPassword = this.passwordEncoder.encode(newUser.getPassword());   
        newUser.setPassword(hashPassword);
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.CREATED,"Create new user" );
        ResCreateUserDTO createUserDTO = new ResCreateUserDTO();
        BeanUtils.copyProperties(this.userService.createUser(newUser), createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(status, createUserDTO,LocalDateTime.now()));
    }
}
