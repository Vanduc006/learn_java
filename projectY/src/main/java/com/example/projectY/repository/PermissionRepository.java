package com.example.projectY.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.projectY.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    public Optional<Permission> findById(Long id);
    // public Optional<Permission> findByName(String name);
    public Boolean existsByMoudleAndApiPathAndMethod(String moudle,String apiPath, String method);
    public Boolean existsByName(String name);
}
