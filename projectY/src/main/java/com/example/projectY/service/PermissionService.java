package com.example.projectY.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.projectY.entity.Permission;

public interface PermissionService {
    // Create
    public Permission handleCreatePermission(Permission newPermission);

    // Read
    public Page<Permission> handleGetAllPermissons(Pageable permissonPageable,Specification<Permission> permissionSpecification);

    public Permission handleGetPermissionById(Long id);

    // Update
    public Permission handleUpdatePermission(Long id, Permission updatePermission);

    // Delete
    public void hanldeDeletePermisson(Long id);

    public List<Permission> handleValidPermissons(List<Permission> list);
}
