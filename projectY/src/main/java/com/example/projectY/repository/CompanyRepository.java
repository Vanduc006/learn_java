package com.example.projectY.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.projectY.entity.Company;


public interface CompanyRepository extends JpaRepository <Company, Long>, JpaSpecificationExecutor<Company> {
    // public list
    public Optional<Company> findById(Long id);
}
