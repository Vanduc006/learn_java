package com.example.projectY.service;

import java.util.List;

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
    public Role handleUpdateRole(Long id, Role updateRole);

    // Delete
    public void handleDeleteRole(Long id);

    public List<Role> handleValidRoles(List<Role> listRoles);

    public Role handleValidRole(Role role);

    // public Boolean handleValidRole(Long id);
}
