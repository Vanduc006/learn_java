package com.example.projectY.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectY.entity.Permission;
import com.example.projectY.entity.Role;
import com.example.projectY.response.ApiResponseDTO;
import com.example.projectY.response.MetaDTO;
import com.example.projectY.response.ResPaginationDTO;
import com.example.projectY.response.ResponseStatusDTO;
import com.example.projectY.service.RoleService;
import com.turkraft.springfilter.boot.Filter;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<ApiResponseDTO<?>> getAllPermissons(
        @Filter Specification<Role> roleSpecification,
        Pageable rolePageable
    ) {
        Page<Role> currentPage = this.roleService.handleGetAllRole(rolePageable, roleSpecification);

        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Get all roles");

        MetaDTO meta = new MetaDTO();
        meta.setPage(rolePageable.getPageNumber()+1);
        meta.setPageSize(rolePageable.getPageSize());
        meta.setPages(currentPage.getTotalPages());
        meta.setTotal(currentPage.getTotalElements());

        ResPaginationDTO<Role, MetaDTO> format = new ResPaginationDTO<Role, MetaDTO>();
        format.setResult(currentPage.getContent());
        format.setMeta(meta);

        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, format, LocalDateTime.now()));
    }

    @PostMapping("/roles")
    public ResponseEntity<ApiResponseDTO<?>> createPermisson(
        @RequestBody Role newrRole
    ) {        
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.CREATED, "Create new role");

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(statusDTO, this.roleService.handleCreateRole(newrRole), LocalDateTime.now()));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<ApiResponseDTO<?>> putMethodName(
        @PathVariable(name = "id") Long id,
        @RequestBody Role udapteRole
    ) {
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Update role");
        
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, this.roleService.handleUpdateRole(id, udapteRole), LocalDateTime.now()));
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<ApiResponseDTO<?>> getMethodName(
        @PathVariable(name = "id") Long id
    ) {
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Get role");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, this.roleService.handleGetRoleById(id),LocalDateTime.now()));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<ApiResponseDTO<?>> deletePermisson(
        @PathVariable(name = "id") Long id
    ) {
        this.roleService.handleDeleteRole(id);
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Delete role");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, null, LocalDateTime.now()));
    } 
    
}
