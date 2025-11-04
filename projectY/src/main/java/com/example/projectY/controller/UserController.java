package com.example.projectY.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.projectY.entity.User;
import com.example.projectY.response.ApiResponeDTO;
import com.example.projectY.response.MetaDTO;
import com.example.projectY.response.ResCreateUserDTO;
import com.example.projectY.response.ResGetUserDTO;
import com.example.projectY.response.ResPaginationDTO;
import com.example.projectY.response.ResUpdateUserDTO;
import com.example.projectY.response.ResponeStatusDTO;
import com.example.projectY.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // @GetMapping("/users")
    // public ResponseEntity<ApiRespone<PageableFormat<User, Meta>>> allUser(
    //     @RequestParam("pageSize") Optional<String> pageSizeOptional,
    //     @RequestParam("current") Optional<String> currentOptional
    // ) {
    //     String pageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : null;
    //     String current = currentOptional.isPresent() ? currentOptional.get() : null;

    //     Pageable pageable = PageRequest.of(Integer.parseInt(current), Integer.parseInt(pageSize));

    //     Page<User> currentPage = this.userService.getAllUser(pageable);

    //     Meta meta = new Meta();
    //     meta.setPage(currentPage.getNumber()+1);
    //     meta.setPageSize(currentPage.getSize());
    //     meta.setPages(currentPage.getTotalPages());
    //     meta.setTotal(currentPage.getTotalElements());
        
    //     PageableFormat<User, Meta> format = new PageableFormat<User, Meta>();
    //     format.setResult(currentPage.getContent());
    //     format.setMeta(meta);

    //     ResponeStatus status = new ResponeStatus(HttpStatus.OK, "Get all user");
    //     return ResponseEntity.ok().body(new ApiRespone<PageableFormat<User, Meta>>(status,format,LocalDateTime.now()));
    // }

    @GetMapping("/users")
    public ResponseEntity<ApiResponeDTO<ResPaginationDTO<ResGetUserDTO, MetaDTO>>> getAllUser(
        @Filter Specification<User> userSpecification,
        Pageable pageable
    ) {
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.OK, "Get all user");

        Page<User> currentPage = this.userService.handleFilterUser(userSpecification,pageable);
        List<ResGetUserDTO> listResGetUserDTO = currentPage.getContent().stream().map(
            user -> {
                ResGetUserDTO resGetUserDTO = new ResGetUserDTO();
                BeanUtils.copyProperties(user, resGetUserDTO);
                if (user.getCompany().getId() == null) {
                    resGetUserDTO.setCompanyUser(null);
                }
                resGetUserDTO.setCompanyUser(this.userService.handleCompanyUser(user.getCompany().getId()));
                
                return resGetUserDTO;
            }).toList();
        MetaDTO meta = new MetaDTO();
        meta.setPage(pageable.getPageNumber()+1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(currentPage.getTotalPages());
        meta.setTotal(currentPage.getTotalElements());
        
        ResPaginationDTO<ResGetUserDTO, MetaDTO> format = new ResPaginationDTO<ResGetUserDTO, MetaDTO>();
        format.setResult(listResGetUserDTO);
        format.setMeta(meta);
        return ResponseEntity.ok().body(new ApiResponeDTO<ResPaginationDTO<ResGetUserDTO, MetaDTO>>(status,format,LocalDateTime.now()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponeDTO<?>> userId(
        @PathVariable("id") Long id
    ) {
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.OK,"Get user by id" );
        User user = this.userService.getUserById(id);
        ResGetUserDTO getUserDTO = new ResGetUserDTO();
        BeanUtils.copyProperties(user, getUserDTO);
        if (user.getCompany().getId() == null) {
            getUserDTO.setCompanyUser(null);
        }
        getUserDTO.setCompanyUser(this.userService.handleCompanyUser(user.getCompany().getId()));
        // getUserDTO
        return ResponseEntity.ok().body(new ApiResponeDTO<>(status, getUserDTO,LocalDateTime.now()));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponeDTO<?>> createUser(
        // @PathVariable Long id,
        @Valid @RequestBody User newUser
    ) {
        String hashPassword = this.passwordEncoder.encode(newUser.getPassword());   
        newUser.setPassword(hashPassword);
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.CREATED,"Create new user" );
        ResCreateUserDTO createUserDTO = new ResCreateUserDTO();
        BeanUtils.copyProperties(this.userService.createUser(newUser), createUserDTO);
        createUserDTO.setCompanyUser(this.userService.handleCompanyUser(newUser.getCompany().getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponeDTO<>(status, createUserDTO,LocalDateTime.now()));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponeDTO<?>> updateUser(
        @PathVariable("id") Long id,
        @Valid @RequestBody User updateUser
    ) {
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.OK, "Update user by id");
        User user = this.userService.getUserById(id);
        ResUpdateUserDTO resUpdateUserDTO = new ResUpdateUserDTO();
        BeanUtils.copyProperties(this.userService.updateUser(id, updateUser), resUpdateUserDTO);
        if (user.getCompany().getId() == null) {
            resUpdateUserDTO.setCompanyUser(null);
        }
        resUpdateUserDTO.setCompanyUser(this.userService.handleCompanyUser(user.getCompany().getId()));
        return ResponseEntity.ok().body(new ApiResponeDTO<>(status, resUpdateUserDTO, LocalDateTime.now()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponeDTO<String>> deleteUser(
        @PathVariable("id") Long id
    ) {
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.OK, "Delete user by id");
        return ResponseEntity.ok().body(new ApiResponeDTO<String>(status, this.userService.deleteUser(id), LocalDateTime.now()));
    }
    
}
