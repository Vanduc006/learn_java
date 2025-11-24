package com.example.projectY.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectY.entity.Job;
import com.example.projectY.entity.Skill;
import com.example.projectY.response.ApiResponseDTO;
import com.example.projectY.response.MetaDTO;
import com.example.projectY.response.ResGetSkillDTO;
import com.example.projectY.response.ResPaginationDTO;
import com.example.projectY.response.ResponseStatusDTO;
import com.example.projectY.response.job.ResCreateJobDTO;
import com.example.projectY.response.job.ResUpdateJobDTO;
import com.example.projectY.service.JobService;
import com.turkraft.springfilter.boot.Filter;

@RestController
@RequestMapping("/api/v1")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping("/jobs") 
    public ResponseEntity<ApiResponseDTO<?>> createJob(
        @RequestBody Job newJob
    ) {
        Job createdJob = this.jobService.handleCreateJob(newJob);
        // Convert DTO skills
        List<ResGetSkillDTO> listResGetSkillDTO = createdJob.getSkills().stream().map(skill -> {
            ResGetSkillDTO resGetSkillDTO = new ResGetSkillDTO();
            resGetSkillDTO.setName(skill.getName());
            return resGetSkillDTO;
        }).toList();
        
        
        ResCreateJobDTO resCreateJobDTO = new ResCreateJobDTO();
        BeanUtils.copyProperties(createdJob, resCreateJobDTO);
        resCreateJobDTO.setSkills(listResGetSkillDTO);
        
        ResponseStatusDTO responseStatusDTO = new ResponseStatusDTO(HttpStatus.CREATED, "Create new job");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(responseStatusDTO, resCreateJobDTO, LocalDateTime.now()));
    }

    @GetMapping("/jobs")
    public ResponseEntity<ApiResponseDTO<?>> getAllJobs(
        @Filter Specification<Job> jobSpecification,
        Pageable jobPageable
    ) {
        Page<Job> currentPage = this.jobService.handleGetAllJob(jobSpecification, jobPageable);

        MetaDTO meta = new MetaDTO();
        meta.setPage(jobPageable.getPageNumber()+1);
        meta.setPageSize(jobPageable.getPageSize());
        meta.setPages(currentPage.getTotalPages());
        meta.setTotal(currentPage.getTotalElements());

        ResPaginationDTO<Job, MetaDTO> format = new ResPaginationDTO<Job, MetaDTO>();
        format.setResult(currentPage.getContent());
        format.setMeta(meta);

        ResponseStatusDTO responseStatusDTO = new ResponseStatusDTO(HttpStatus.OK, "Get all jobs");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(responseStatusDTO, format, LocalDateTime.now()));
    }

    @PutMapping("/jobs/{id}")
    public ResponseEntity<ApiResponseDTO<?>> updateJob(
        @PathVariable(name = "id") Long id,
        @RequestBody Job updateJob
    ) {
        Job updatedJob = this.jobService.handleUpdateJob(id, updateJob);
        // Convert to DTO
        List<ResGetSkillDTO> listSkillDTOs = updatedJob.getSkills().stream().map(skill -> {
            ResGetSkillDTO resGetSkillDTO = new ResGetSkillDTO();
            resGetSkillDTO.setName(skill.getName());
            return resGetSkillDTO;
        }).toList();

        ResUpdateJobDTO resUpdateJobDTO = new ResUpdateJobDTO();
        BeanUtils.copyProperties(updatedJob, resUpdateJobDTO);
        resUpdateJobDTO.setSkills(listSkillDTOs);

        ResponseStatusDTO responseStatusDTO = new ResponseStatusDTO(HttpStatus.OK, "Update job");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(responseStatusDTO, resUpdateJobDTO, LocalDateTime.now()));
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<ApiResponseDTO<?>> deleteJob(
        @PathVariable("id") Long id
    ) {
        this.jobService.handleDeleteJob(id);
        ResponseStatusDTO responseStatusDTO = new ResponseStatusDTO(HttpStatus.OK, "Delete job");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(responseStatusDTO, null, LocalDateTime.now()));
    }
}
