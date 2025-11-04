package com.example.projectY.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectY.entity.Company;
import com.example.projectY.response.ApiResponeDTO;
import com.example.projectY.response.MetaDTO;
import com.example.projectY.response.ResPaginationDTO;
import com.example.projectY.response.ResponeStatusDTO;
import com.example.projectY.service.CompanyService;
import com.example.projectY.utils.annotations.ApiAnnotation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/companies")
    public ResponseEntity<ApiResponeDTO<?>> createCompany(
        @Valid @RequestBody Company newCompany
    ) {
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.CREATED, "Create company");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponeDTO<>(status, this.companyService.handleCreateCompanies(newCompany),LocalDateTime.now()));
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
    public ResponseEntity<ApiResponeDTO<ResPaginationDTO<Company, MetaDTO>>> getAllCompany(
        @Filter Specification<Company> comapnySpecification,
        Pageable companyPageable
    ) {
        
        Page<Company> currentPage = this.companyService.handleFilterCompany(comapnySpecification, companyPageable);
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.OK, "Get all companies");
        // status.setStatusMessage().get;

        MetaDTO meta = new MetaDTO();
        meta.setPage(companyPageable.getPageNumber()+1);
        meta.setPageSize(companyPageable.getPageSize());
        meta.setPages(currentPage.getTotalPages());
        meta.setTotal(currentPage.getTotalElements());

        ResPaginationDTO<Company, MetaDTO> format = new ResPaginationDTO<Company, MetaDTO>();
        format.setResult(currentPage.getContent());
        format.setMeta(meta);

        return ResponseEntity.ok().body(new ApiResponeDTO<>(status, format, LocalDateTime.now()));
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<ApiResponeDTO<Company>> updateCompany(
        @PathVariable("id") Long id,
        @RequestBody Company updateCompany
    ) {
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.OK, "Update company");

        return ResponseEntity.ok().body(new ApiResponeDTO<>(status, this.companyService.handleUpdateCompanies(id, updateCompany), LocalDateTime.now()));
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<ApiResponeDTO<?>> deleteCompany(
        @PathVariable("id") Long id
    ) {
        Company company = this.companyService.handleGetCompanyById(id);
        this.companyService.handleDeleteCompanies(company.getId());
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.OK, "Delete company");
        return ResponseEntity.ok().body(new ApiResponeDTO<>(status, null, LocalDateTime.now()));
    }

    
    // @GetMapping("/filter")
    // public ResponseEntity<ApiRespone<?>> filterCompany(
    //     @Filter Specification<Company> companySpecification
    // ) {
    //     ResponeStatus status = new ResponeStatus(HttpStatus.OK, "Filter company");
    //     return ResponseEntity.ok().body(new ApiRespone<>(status, this.companyService.handleFilterCompany(companySpecification), LocalDateTime.now()));
    // }
}
