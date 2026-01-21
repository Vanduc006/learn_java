package com.example.projectY.service.impliment;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.projectY.entity.Permission;
import com.example.projectY.repository.PermissionRepository;
import com.example.projectY.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    private PermissionRepository permissionRepository;

    // @Autowired
    // private RoleService roleService;
    // Create
    public Permission handleCreatePermission(Permission newPermission) {

        if (this.permissionRepository.existsByMoudleAndApiPathAndMethod(newPermission.getMoudle(), newPermission.getApiPath(), newPermission.getMethod())) {
            throw new DuplicateKeyException("Permisson already exists");
        }
        if (this.permissionRepository.existsByName(newPermission.getName())) {
            throw new DuplicateKeyException("Permisson already exists");
        }
        return this.permissionRepository.save(newPermission);
    }

    // Read
    public Page<Permission> handleGetAllPermissons(Pageable permissonPageable,Specification<Permission> permissionSpecification) {
        return this.permissionRepository.findAll(permissionSpecification,permissonPageable);
    }

    public Permission handleGetPermissionById(Long id) {
        Optional<Permission> optionalPermisson = this.permissionRepository.findById(id);
        if (!optionalPermisson.isPresent()) {
            throw new NoSuchElementException("Permission not found");
        }
        return optionalPermisson.get();
    }

    // Update
    public Permission handleUpdatePermission(Long id, Permission updatePermission) {
        Optional<Permission> optionalPermisson = this.permissionRepository.findById(id);
        if (!optionalPermisson.isPresent()) {
            throw new NoSuchElementException("Permission not found");
        }
        if (this.permissionRepository.existsByMoudleAndApiPathAndMethod(updatePermission.getMoudle(), updatePermission.getApiPath(), updatePermission.getMethod())) {
            throw new DuplicateKeyException("Permisson already exists");
        }
        if (this.permissionRepository.existsByName(updatePermission.getName())) {
            throw new DuplicateKeyException("Permisson already exists");
        }
        
        Permission currentPermission = optionalPermisson.get();
        BeanUtils.copyProperties(updatePermission, currentPermission, "id", "createdAt", "createdBy");
        return this.permissionRepository.save(currentPermission);
    }

    // Delete
    public void hanldeDeletePermisson(Long id) {
        Optional<Permission> optionalPermisson = this.permissionRepository.findById(id);
        if (!optionalPermisson.isPresent()) {
            throw new NoSuchElementException("Permission not found");
        }
        // detelte in Role
        optionalPermisson.get().getRoles().forEach(role -> role.getPermissions().remove(optionalPermisson.get()));
        this.permissionRepository.delete(optionalPermisson.get());
    }

    public List<Permission> handleValidPermissons(List<Permission> list) {
        List<Permission> validPermisson = list.stream()
        .map(permisson -> this.permissionRepository.findById(permisson.getId()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();

        return validPermisson;
    }
}
