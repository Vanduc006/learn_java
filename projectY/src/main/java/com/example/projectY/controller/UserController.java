package com.example.projectY.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.projectY.entity.User;
import com.example.projectY.response.ApiResponseDTO;
import com.example.projectY.response.MetaDTO;
import com.example.projectY.response.ResPaginationDTO;
import com.example.projectY.response.ResponseStatusDTO;
import com.example.projectY.response.user.ResCreateUserDTO;
import com.example.projectY.response.user.ResGetUserDTO;
import com.example.projectY.response.user.ResUpdateUserDTO;
import com.example.projectY.service.UserService;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1")
public class UserController {
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
    public ResponseEntity<ApiResponseDTO<ResPaginationDTO<ResGetUserDTO, MetaDTO>>> getAllUser(
        @Filter Specification<User> userSpecification,
        Pageable pageable
    ) {
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.OK, "Get all user");

        Page<User> currentPage = this.userService.handleFilterUser(userSpecification,pageable);
        List<ResGetUserDTO> listResGetUserDTO = currentPage.getContent().stream().map(
            user -> {
                ResGetUserDTO resGetUserDTO = new ResGetUserDTO();
                BeanUtils.copyProperties(user, resGetUserDTO);
                if (user.getCompany() != null) {
                    resGetUserDTO.setCompanyUser(this.userService.handleCompanyUser(user.getCompany().getId()));
                }
                if (user.getRole() != null) {
                    resGetUserDTO.setRoleUser(this.userService.handleRoleUserDTO(user.getRole().getId()));
                } 
                
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
        return ResponseEntity.ok().body(new ApiResponseDTO<ResPaginationDTO<ResGetUserDTO, MetaDTO>>(status,format,LocalDateTime.now()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponseDTO<?>> userId(
        @PathVariable("id") Long id
    ) {
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.OK,"Get user by id" );
        User user = this.userService.getUserById(id);
        ResGetUserDTO getUserDTO = new ResGetUserDTO();
        BeanUtils.copyProperties(user, getUserDTO);
        if (user.getCompany() != null) {
            getUserDTO.setCompanyUser(this.userService.handleCompanyUser(user.getCompany().getId()));
        }
        if (user.getRole() != null) {
            getUserDTO.setRoleUser(this.userService.handleRoleUserDTO(user.getRole().getId()));
        } 
        getUserDTO.setCompanyUser(this.userService.handleCompanyUser(user.getCompany().getId()));
        getUserDTO.setRoleUser(this.userService.handleRoleUserDTO(user.getRole().getId()));

        // getUserDTO
        return ResponseEntity.ok().body(new ApiResponseDTO<>(status, getUserDTO,LocalDateTime.now()));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponseDTO<?>> createUser(
        // @PathVariable Long id,
        @Valid @RequestBody User newUser
    ) {
        String hashPassword = this.passwordEncoder.encode(newUser.getPassword());   
        newUser.setPassword(hashPassword);
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.CREATED,"Create new user" );
        ResCreateUserDTO createUserDTO = new ResCreateUserDTO();
        BeanUtils.copyProperties(this.userService.createUser(newUser), createUserDTO);
        if (newUser.getCompany() != null) {
            createUserDTO.setCompanyUser(this.userService.handleCompanyUser(newUser.getCompany().getId()));
        }
        if (newUser.getRole() != null) {
            createUserDTO.setRoleUser(this.userService.handleRoleUserDTO(newUser.getRole().getId()));
        } 
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(status, createUserDTO,LocalDateTime.now()));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponseDTO<?>> updateUser(
        @PathVariable("id") Long id,
        @RequestBody User updateUser
    ) {
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.OK, "Update user by id");
        User user = this.userService.updateUser(id, updateUser);
        ResUpdateUserDTO resUpdateUserDTO = new ResUpdateUserDTO();
        BeanUtils.copyProperties(
            user, resUpdateUserDTO
        );

        if (user.getCompany() != null) {
            resUpdateUserDTO.setCompanyUser(this.userService.handleCompanyUser(user.getCompany().getId()));
        }
        if (user.getRole() != null) {
            resUpdateUserDTO.setRoleUser(this.userService.handleRoleUserDTO(user.getRole().getId()));
        } 
        resUpdateUserDTO.setCompanyUser(this.userService.handleCompanyUser(user.getCompany().getId()));
        resUpdateUserDTO.setRoleUser(this.userService.handleRoleUserDTO(user.getRole().getId()));
        return ResponseEntity.ok().body(new ApiResponseDTO<>(status, resUpdateUserDTO, LocalDateTime.now()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteUser(
        @PathVariable("id") Long id
    ) {
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.OK, "Delete user by id");
        return ResponseEntity.ok().body(new ApiResponseDTO<String>(status, this.userService.deleteUser(id), LocalDateTime.now()));
    }
    
}
