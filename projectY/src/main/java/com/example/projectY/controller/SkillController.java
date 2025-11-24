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
import com.example.projectY.entity.Skill;
import com.example.projectY.response.ApiResponseDTO;
import com.example.projectY.response.MetaDTO;
import com.example.projectY.response.ResPaginationDTO;
import com.example.projectY.response.ResponseStatusDTO;
import com.example.projectY.service.SkillService;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class SkillController {
    @Autowired 
    private SkillService skillService;

    @PostMapping("/skills")
    public ResponseEntity<ApiResponseDTO<?>> createSkill(
        @Valid @RequestBody Skill newSkill
    ) {
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.CREATED, "Create new skill");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(statusDTO, this.skillService.handleCreateSkill(newSkill), LocalDateTime.now()));
    }

    @GetMapping("/skills")
    public ResponseEntity<ApiResponseDTO<?>> getAllSkill(
        @Filter Specification<Skill> skillSpecification,
        Pageable skillPageable
    ) {
        Page<Skill> currentPage = this.skillService.handleFilterSkill(skillSpecification, skillPageable);
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Get all skills");

        MetaDTO meta = new MetaDTO();
        meta.setPage(skillPageable.getPageNumber()+1);
        meta.setPageSize(skillPageable.getPageSize());
        meta.setPages(currentPage.getTotalPages());
        meta.setTotal(currentPage.getTotalElements());

        ResPaginationDTO<Skill, MetaDTO> format = new ResPaginationDTO<Skill, MetaDTO>();
        format.setResult(currentPage.getContent());
        format.setMeta(meta);

        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, format, LocalDateTime.now()));
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<ApiResponseDTO<?>> getSkill(
        @PathVariable(name = "id") Long id
    ) {
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Get skill");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, this.skillService.handleGetSkillById(id), LocalDateTime.now()));
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<ApiResponseDTO<?>> updateSkill(
        @PathVariable(name = "id") Long id,
        @Valid @RequestBody Skill updateSkill
    ) {
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Update skill");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, this.skillService.handleUpdateSkill(id, updateSkill), LocalDateTime.now()));
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<ApiResponseDTO<?>> deleteSkill(
        @PathVariable(name = "id") Long id
    ) {
        this.skillService.handleDeleteSkill(id);
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Delete skill");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, null, LocalDateTime.now()));
    }
}
