package com.example.projectY.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectY.entity.Company;
import com.example.projectY.response.ApiResponseDTO;
import com.example.projectY.response.MetaDTO;
import com.example.projectY.response.ResPaginationDTO;
import com.example.projectY.response.ResponseStatusDTO;
import com.example.projectY.service.CompanyService;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/companies")
    public ResponseEntity<ApiResponseDTO<?>> createCompany(
        @Valid @RequestBody Company newCompany
    ) {
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.CREATED, "Create company");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(status, this.companyService.handleCreateCompanies(newCompany),LocalDateTime.now()));
    }

    // @GetMapping("/companies")
    // public ResponseEntity<ApiRespone<PageableFormat<Company, Meta>>> getAllCompany(
    //     @RequestParam(name = "pageSize", defaultValue = "2", required = false) Optional<String> pageSizeOptional,
    //     @RequestParam(name = "current", defaultValue = "1", required = false) Optional<String> currentOptional
    // ) {
    //     String pageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : null;
    //     String current = currentOptional.isPresent() ? currentOptional.get() : null;
    //     Pageable pageable = PageRequest.of(Integer.parseInt(current)-1, Integer.parseInt(pageSize));

    //     Page<Company> currentPage = this.companyService.handleGetAllCompanies(pageable);
    //     ResponeStatus status = new ResponeStatus(HttpStatus.OK, "Get all companies");

    //     Meta meta = new Meta();
    //     meta.setPage(currentPage.getNumber()+1);
    //     meta.setPageSize(currentPage.getSize());
    //     meta.setPages(currentPage.getTotalPages());
    //     meta.setTotal(currentPage.getTotalElements());

    //     PageableFormat<Company, Meta> format = new PageableFormat<Company, Meta>();
    //     format.setResult(currentPage.getContent());
    //     format.setMeta(meta);


    //     return ResponseEntity.ok().body(new ApiRespone<PageableFormat<Company, Meta>> (status, format, LocalDateTime.now()));
    // }
    @GetMapping("/companies")
    // @ApiAnnotation("test annotaion")
    public ResponseEntity<ApiResponseDTO<ResPaginationDTO<Company, MetaDTO>>> getAllCompany(
        @Filter Specification<Company> comapnySpecification,
        Pageable companyPageable
    ) {
        
        Page<Company> currentPage = this.companyService.handleFilterCompany(comapnySpecification, companyPageable);
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.OK, "Get all companies");
        // status.setStatusMessage().get;

        MetaDTO meta = new MetaDTO();
        meta.setPage(companyPageable.getPageNumber()+1);
        meta.setPageSize(companyPageable.getPageSize());
        meta.setPages(currentPage.getTotalPages());
        meta.setTotal(currentPage.getTotalElements());

        ResPaginationDTO<Company, MetaDTO> format = new ResPaginationDTO<Company, MetaDTO>();
        format.setResult(currentPage.getContent());
        format.setMeta(meta);

        return ResponseEntity.ok().body(new ApiResponseDTO<>(status, format, LocalDateTime.now()));
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<ApiResponseDTO<Company>> updateCompany(
        @PathVariable("id") Long id,
        @RequestBody Company updateCompany
    ) {
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.OK, "Update company");

        return ResponseEntity.ok().body(new ApiResponseDTO<>(status, this.companyService.handleUpdateCompanies(id, updateCompany), LocalDateTime.now()));
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<ApiResponseDTO<?>> deleteCompany(
        @PathVariable("id") Long id
    ) {
        Company company = this.companyService.handleGetCompanyById(id);
        this.companyService.handleDeleteCompanies(company.getId());
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.OK, "Delete company");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(status, null, LocalDateTime.now()));
    }

    
    // @GetMapping("/filter")
    // public ResponseEntity<ApiRespone<?>> filterCompany(
    //     @Filter Specification<Company> companySpecification
    // ) {
    //     ResponeStatus status = new ResponeStatus(HttpStatus.OK, "Filter company");
    //     return ResponseEntity.ok().body(new ApiRespone<>(status, this.companyService.handleFilterCompany(companySpecification), LocalDateTime.now()));
    // }
}
