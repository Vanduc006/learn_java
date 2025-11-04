package com.example.projectY.service.impliment;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.projectY.controller.CompanyController;
import com.example.projectY.entity.Company;
import com.example.projectY.entity.User;
// import com.example.projectY.entity.Companies;
import com.example.projectY.repository.CompanyRepository;
import com.example.projectY.repository.UserRepository;
import com.example.projectY.service.CompanyService;
import com.example.projectY.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserService userService;

    @Override
    public Company handleCreateCompanies(Company newCompanies) {
        return this.companyRepository.save(newCompanies);
    } // Create

    @Override
    public Page<Company> handleGetAllCompanies(Pageable pageable) {
        return this.companyRepository.findAll(pageable);
    }
    
    @Transactional
    @Override
    public Company handleUpdateCompanies(Long id, Company updateCompany) throws NoSuchElementException{
        Optional<Company> optionalComapny = this.companyRepository.findById(id);
        if (!optionalComapny.isPresent()) {
            throw new NoSuchElementException("Company not found");
        }
        Company currentCompany = optionalComapny.get();
        if (updateCompany.getName() != null) {
            currentCompany.setName(updateCompany.getName());
        }
        if (updateCompany.getAddress() != null) {
            currentCompany.setAddress(updateCompany.getAddress());
        }
        if (updateCompany.getDescription() != null) {
            currentCompany.setDescription(updateCompany.getDescription());
        }
        if (updateCompany.getLogo() != null) {
            currentCompany.setLogo(updateCompany.getLogo());
        }

        return this.companyRepository.saveAndFlush(currentCompany);
    }

    @Transactional
    @Override
    public void handleDeleteCompanies(Long id) {
        Optional<Company> optionalCompany = this.companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            this.userService.deleteUserByCompany(optionalCompany.get());
        }
        this.companyRepository.deleteById(id);
    }

    @Override
    public Page<Company> handleFilterCompany(Specification<Company> companySpecification, Pageable companyPageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(companySpecification, companyPageable);
        return pageCompany;
    }

    @Override
    public Company handleGetCompanyById(Long id) {
        Optional<Company> optionalCompany = this.companyRepository.findById(id);
        if (!optionalCompany.isPresent()) {
            throw new NoSuchElementException("Company not found");
        }
        return optionalCompany.get();
    }
}
