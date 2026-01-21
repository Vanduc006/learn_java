package com.example.projectY.controller;

import java.time.LocalDate;
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
import com.example.projectY.response.ApiResponseDTO;
import com.example.projectY.response.MetaDTO;
import com.example.projectY.response.ResPaginationDTO;
import com.example.projectY.response.ResponseStatusDTO;
import com.example.projectY.response.resume.ResGetResumeDTO;
import com.example.projectY.service.PermissionService;
import com.turkraft.springfilter.boot.Filter;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/v1")
public class PermissonController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/permissons")
    public ResponseEntity<ApiResponseDTO<?>> getAllPermissons(
        @Filter Specification<Permission> permissonSpecification,
        Pageable permissonPageable
    ) {
        Page<Permission> currentPage = this.permissionService.handleGetAllPermissons(permissonPageable, permissonSpecification);

        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Get all permissons");

        MetaDTO meta = new MetaDTO();
        meta.setPage(permissonPageable.getPageNumber()+1);
        meta.setPageSize(permissonPageable.getPageSize());
        meta.setPages(currentPage.getTotalPages());
        meta.setTotal(currentPage.getTotalElements());

        ResPaginationDTO<Permission, MetaDTO> format = new ResPaginationDTO<Permission, MetaDTO>();
        format.setResult(currentPage.getContent());
        format.setMeta(meta);

        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, format, LocalDateTime.now()));
    }

    @PostMapping("/permissons")
    public ResponseEntity<ApiResponseDTO<?>> createPermisson(
        @RequestBody Permission newPermission
    ) {        
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.CREATED, "Create new permisson");

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(statusDTO, this.permissionService.handleCreatePermission(newPermission), LocalDateTime.now()));
    }

    @PutMapping("/permissons/{id}")
    public ResponseEntity<ApiResponseDTO<?>> putMethodName(
        @PathVariable(name = "id") Long id,
        @RequestBody Permission updatePermission
    ) {
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Update permisson");
        
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, this.permissionService.handleUpdatePermission(id, updatePermission), LocalDateTime.now()));
    }

    @GetMapping("/permissons/{id}")
    public ResponseEntity<ApiResponseDTO<?>> getMethodName(
        @PathVariable(name = "id") Long id
    ) {
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Get permisson");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, this.permissionService.handleGetPermissionById(id),LocalDateTime.now()));
    }

    @DeleteMapping("/permissons/{id}")
    public ResponseEntity<ApiResponseDTO<?>> deletePermisson(
        @PathVariable(name = "id") Long id
    ) {
        this.permissionService.hanldeDeletePermisson(id);
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Delete permisson");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, null, LocalDateTime.now()));
    } 
    
    
}
