package com.example.projectY.service.impliment;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.projectY.entity.Job;
import com.example.projectY.entity.Resume;
import com.example.projectY.entity.User;
import com.example.projectY.repository.ResumeRepository;
import com.example.projectY.service.JobService;
import com.example.projectY.service.ResumeService;
import com.example.projectY.service.UserService;

@Service
public class ResumeServiceImpl implements ResumeService{
    @Autowired 
    private ResumeRepository resumeRepository;

    @Autowired
    private UserService userService;

    @Autowired 
    private JobService jobService;

    public Resume handleCreateResume(Resume newResume) {
        if (!this.userService.handleValidUser(newResume.getUser().getId())) {
            throw new NoSuchElementException("User not found");
        }

        if (!this.jobService.handleValidJob(newResume.getJob().getId())) {
            throw new NoSuchElementException("Job not found");
        }
        
        return this.resumeRepository.save(newResume);
    }

    public Page<Resume> handleGetAllResume(Pageable resumePageable, Specification<Resume> resumSpecification) {
        return this.resumeRepository.findAll(resumSpecification, resumePageable);
    }

    public Resume handleUpdateResume(Long id, Resume updateResume) {
        Optional<Resume> optionalResume = this.resumeRepository.findById(id);
        if (!optionalResume.isPresent()) {
            throw new NoSuchElementException("Resume not found");
        }

        Resume currentResume = optionalResume.get();
        // Only update status
        if (updateResume.getStatus() != null) {
            currentResume.setStatus(updateResume.getStatus());
        }
        // BeanUtils.copyProperties(updateResume, currentResume, "id", "createdAt", "createdBy");
        return this.resumeRepository.save(currentResume);
    }

    public void handleDeleteResume(Long id) {
        Optional<Resume> optionalResume = this.resumeRepository.findById(id);
        if (!optionalResume.isPresent()) {
            throw new NoSuchElementException("Resume not found");
        }
        Resume currentResume = optionalResume.get();
        this.resumeRepository.delete(currentResume);
    }

    public Resume handleGetResumeById(Long id) {
        Optional<Resume> optionalResume = this.resumeRepository.findById(id);
        if (!optionalResume.isPresent()) {
            throw new NoSuchElementException("Resume not found");
        }
        Resume currentResume = optionalResume.get();
        return currentResume;
    }

    // public void deleteResumeByUser(User user) {
    //     List<Resume> currentList = this.resumeRepository.findByUser(user);
    //     this.resumeRepository.deleteAll(currentList);
    // }

    // public void deleteResumeByJob(Job job) {
    //     List<Resume> currenList = this.resumeRepository.findByJob(job);
    //     this.resumeRepository.deleteAll(currenList);
    // }
}
