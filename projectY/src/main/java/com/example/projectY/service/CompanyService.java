package com.example.projectY.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.projectY.entity.Company;

public interface CompanyService {
    public Company handleCreateCompanies(Company newCompanies); // Create

    public Page<Company> handleGetAllCompanies(Pageable pageable); // Todo implement to List
    // // public <Optional> handle
    public Company handleUpdateCompanies(Long id, Company updateCompanies);

    public void handleDeleteCompanies(Long id);

    public Page<Company> handleFilterCompany(Specification<Company> companySpecification, Pageable comapnyPageable);

    public Company handleGetCompanyById(Long id);
}
