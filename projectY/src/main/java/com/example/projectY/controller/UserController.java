package com.example.projectY.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.projectY.entity.ApiRespone;
import com.example.projectY.entity.ResponeStatus;
import com.example.projectY.entity.User;
import com.example.projectY.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UserController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/users")
    public ResponseEntity<ApiRespone<List<User>>> allUser() {
        ResponeStatus status = new ResponeStatus(HttpStatus.OK, "Get all user");
        return ResponseEntity.ok().body(new ApiRespone<List<User>>(status,this.userService.getAllUser(),LocalDateTime.now()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiRespone<User>> userId(
        @PathVariable("id") Long id
    ) {
        ResponeStatus status = new ResponeStatus(HttpStatus.OK,"Get user by id" );
        return ResponseEntity.ok().body(new ApiRespone<User>(status, this.userService.getUserById(id),LocalDateTime.now()));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiRespone<User>> createUser(
        // @PathVariable Long id,
        @Valid @RequestBody User newUser
    ) {
        String hashPassword = this.passwordEncoder.encode(newUser.getPassword());   
        newUser.setPassword(hashPassword);
        ResponeStatus status = new ResponeStatus(HttpStatus.CREATED,"Create new user" );
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiRespone<User>(status, this.userService.createUser(newUser),LocalDateTime.now()));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiRespone<User>> updateUser(
        @PathVariable("id") Long id,
        @Valid @RequestBody User updateUser
    ) {
        ResponeStatus status = new ResponeStatus(HttpStatus.OK, "Update user by id");
        return ResponseEntity.ok().body(new ApiRespone<User>(status, this.userService.updateUser(id, updateUser), LocalDateTime.now()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiRespone<String>> deleteUser(
        @PathVariable("id") Long id
    ) {
        ResponeStatus status = new ResponeStatus(HttpStatus.OK, "Delete user by id");
        return ResponseEntity.ok().body(new ApiRespone<String>(status, this.userService.deleteUser(id), LocalDateTime.now()));
    }
    
}
