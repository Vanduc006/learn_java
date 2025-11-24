package com.example.projectY.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
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

import com.example.projectY.entity.Job;
import com.example.projectY.entity.Resume;
import com.example.projectY.response.ApiResponseDTO;
import com.example.projectY.response.MetaDTO;
import com.example.projectY.response.ResPaginationDTO;
import com.example.projectY.response.ResponseStatusDTO;
import com.example.projectY.response.job.ResUpdateJobDTO;
import com.example.projectY.response.resume.ResCreateResumeDTO;
import com.example.projectY.response.resume.ResGetResumeDTO;
import com.example.projectY.response.resume.ResGetResumeDTO.JobResumeDTO;
import com.example.projectY.response.resume.ResGetResumeDTO.UserResumeDTO;
import com.example.projectY.service.ResumeService;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ResumeController {
    
    @Autowired
    private ResumeService resumeService;

    @PostMapping("/resumes")
    public ResponseEntity<ApiResponseDTO<?>> createResume(
        @Valid @RequestBody Resume newResume
    ) {
        Resume createdResume = this.resumeService.handleCreateResume(newResume);
        ResCreateResumeDTO resCreateResumeDTO = new ResCreateResumeDTO();
        resCreateResumeDTO.setId(createdResume.getId());
        resCreateResumeDTO.setCreatedAt(createdResume.getCreatedAt());
        resCreateResumeDTO.setCreatedBy(createdResume.getCreatedBy());

        ResponseStatusDTO responseStatusDTO = new ResponseStatusDTO(HttpStatus.CREATED, "Create new resume");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(responseStatusDTO, resCreateResumeDTO, LocalDateTime.now()));
    }

    @GetMapping("/resumes")
    public ResponseEntity<ApiResponseDTO<?>> getAllResume(
        @Filter Specification<Resume> resumSpecification,
        Pageable resumePageable
    ) {
        Page<Resume> currentPage = this.resumeService.handleGetAllResume(resumePageable, resumSpecification);

        List<ResGetResumeDTO> resGetResumeDTOs = currentPage.getContent().stream().map(resume -> {
            ResGetResumeDTO resGetResumeDTO = new ResGetResumeDTO();
            BeanUtils.copyProperties(resume, resGetResumeDTO);

            ResGetResumeDTO.UserResumeDTO userResumeDTO = new UserResumeDTO();
            BeanUtils.copyProperties(resume.getUser(), userResumeDTO);
            resGetResumeDTO.setUser(userResumeDTO);

            ResGetResumeDTO.JobResumeDTO jobResumeDTO = new JobResumeDTO();
            BeanUtils.copyProperties(resume.getJob(), jobResumeDTO);
            resGetResumeDTO.setJob(jobResumeDTO);

            return resGetResumeDTO;
        }).toList();

        MetaDTO meta = new MetaDTO();
        meta.setPage(resumePageable.getPageNumber()+1);
        meta.setPageSize(resumePageable.getPageSize());
        meta.setPages(currentPage.getTotalPages());
        meta.setTotal(currentPage.getTotalElements());

        ResPaginationDTO<ResGetResumeDTO, MetaDTO> format = new ResPaginationDTO<ResGetResumeDTO, MetaDTO>();
        format.setResult(resGetResumeDTOs);
        format.setMeta(meta);
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Get all resumes");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, format, LocalDateTime.now()));
    }

    @PutMapping("/resumes/{id}")
    public ResponseEntity<ApiResponseDTO<?>> updateResume(
        @PathVariable(name = "id") Long id,
        @RequestBody Resume updateResume
    ) {
        Resume updatedResume = this.resumeService.handleUpdateResume(id, updateResume);
        ResUpdateJobDTO resUpdateJobDTO = new ResUpdateJobDTO();
        resUpdateJobDTO.setUpdatedAt(updatedResume.getCreatedAt());
        resUpdateJobDTO.setUpdatedBy(updatedResume.getUpdatedBy());

        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Update resume");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, resUpdateJobDTO, LocalDateTime.now()));
    }

    @GetMapping("/resumes/{id}")
    public ResponseEntity<ApiResponseDTO<?>> getResumeById(
        @PathVariable(name = "id") Long id
    ) {
        Resume currentResume = this.resumeService.handleGetResumeById(id);
        ResGetResumeDTO resGetResumeDTO = new ResGetResumeDTO();
        BeanUtils.copyProperties(currentResume, resGetResumeDTO);

        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Get resume");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, resGetResumeDTO, LocalDateTime.now()));
    }

    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<ApiResponseDTO<?>> deleteResume(
        @PathVariable(name = "id") Long id
    ) {
        this.resumeService.handleDeleteResume(id);
        ResponseStatusDTO statusDTO = new ResponseStatusDTO(HttpStatus.OK, "Delete resume");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(statusDTO, null, LocalDateTime.now()));
    }
}
