package com.example.projectY.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.projectY.entity.Job;
import com.example.projectY.entity.Resume;
import com.example.projectY.entity.User;

public interface ResumeService {
    public Resume handleCreateResume(Resume newResume);

    public Page<Resume> handleGetAllResume(Pageable resumePageable, Specification<Resume> resumSpecification);

    public Resume handleUpdateResume(Long id, Resume updateResume); 

    public void handleDeleteResume(Long id);

    public Resume handleGetResumeById(Long id);

    public Page<Resume> handleGetResumeByUser(Pageable resumePageable);

    // public void deleteResumeByUser(User user);

    // public void deleteResumeByJob(Job job);
}
