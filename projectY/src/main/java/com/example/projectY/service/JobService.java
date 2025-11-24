package com.example.projectY.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.projectY.entity.Company;
import com.example.projectY.entity.Job;

public interface JobService {
    // Create
    public Job handleCreateJob(Job newJob);
    // Get
    public Page<Job> handleGetAllJob(Specification<Job> jobSpecification, Pageable joPageable);
    public Job handleGetJobById(Long id);
    // Update
    public Job handleUpdateJob(Long id, Job updateJob);
    // Delete
    public void handleDeleteJob(Long id);

    public Boolean handleValidJob(Long id);

    public void handleDeleteJobByCompany(Company company);
}
