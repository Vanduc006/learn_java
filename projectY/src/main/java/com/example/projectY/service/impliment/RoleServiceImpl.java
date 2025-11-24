package com.example.projectY.service.impliment;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.projectY.entity.Permission;
import com.example.projectY.entity.Role;
import com.example.projectY.entity.User;
import com.example.projectY.repository.RoleRepository;
import com.example.projectY.service.PermissionService;
import com.example.projectY.service.RoleService;
import com.example.projectY.service.UserService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    // Create
    public Role handleCreateRole(Role newRole) {
        // valid permisson
        List<Permission> validListPermisson = this.permissionService.handleValidPermissons(newRole.getPermissions());
        newRole.setPermissions(validListPermisson);
        // valid user
        List<User> validListUser = this.userService.handleValidUsers(newRole.getUsers());
        newRole.setUsers(validListUser);

        return this.roleRepository.save(newRole);
    }
    
    // Get
    public Page<Role> handleGetAllRole(Pageable rolePageable,Specification<Role> roleSpecification) {
        return this.roleRepository.findAll(roleSpecification, rolePageable);
    }

    public Role handleGetRoleById(Long id) {
        Optional<Role> optionalRole = this.roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            throw new NoSuchElementException("Role not found");
        }
        return optionalRole.get();
    }

    // Update
    public Role handleUpdateRole(Role updateRole) {
        Optional<Role> optionalRole = this.roleRepository.findById(updateRole.getId());
        if (!optionalRole.isPresent()) {
            throw new NoSuchElementException("Role not found");
        }
        
        // valid permisson
        List<Permission> validListPermisson = this.permissionService.handleValidPermissons(updateRole.getPermissions());
        updateRole.setPermissions(validListPermisson);
        // valid user
        List<User> validListUser = this.userService.handleValidUsers(updateRole.getUsers());
        updateRole.setUsers(validListUser);

        Role currentRole = optionalRole.get();
        BeanUtils.copyProperties(updateRole, currentRole, "id", "createdAt", "createdBy");
        return this.roleRepository.save(currentRole);
    }

    // Delete
    public void handleDeleteRole(Long id) {
        Optional<Role> optionalRole = this.roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            throw new NoSuchElementException("Role not found");
        }
        if (optionalRole.isPresent()) {
            this.userService.deleteUserByRole(optionalRole.get());
        }
        this.roleRepository.delete(optionalRole.get());
    }

    // public Boolean handleValidRole(Long id);
}
