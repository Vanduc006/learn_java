package com.example.projectY.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.projectY.entity.Role;

public interface RoleService {
    // Create
    public Role handleCreateRole(Role newRole);
    
    // Get
    public Page<Role> handleGetAllRole(Pageable rolePageable,Specification<Role> roleSpecification);

    public Role handleGetRoleById(Long id);

    // Update
    public Role handleUpdateRole(Role updateRole);

    // Delete
    public void handleDeleteRole(Long id);

    // public Boolean handleValidRole(Long id);
}
