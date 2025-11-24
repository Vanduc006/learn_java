package com.example.projectY.service.impliment;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.projectY.controller.SkillController;
import com.example.projectY.entity.Company;
import com.example.projectY.entity.Job;
import com.example.projectY.entity.Resume;
import com.example.projectY.entity.Skill;
import com.example.projectY.repository.JobRepository;
import com.example.projectY.repository.ResumeRepository;
import com.example.projectY.response.ResGetSkillDTO;
import com.example.projectY.service.JobService;
import com.example.projectY.service.ResumeService;
import com.example.projectY.service.SkillService;

@Service
public class JobServiceImpl implements JobService{

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillService skillService;

    // @Autowired ResumeService resumeService;
    @Autowired
    private ResumeRepository resumeRepository;

    // Create
    public Job handleCreateJob(Job newJob) {
        // Valid skills
        if (newJob.getSkills() != null) {
            List<Skill> listSkills = this.skillService.handleValidSkills(newJob.getSkills());
            newJob.setSkills(listSkills);
        }
        return this.jobRepository.save(newJob);

    }
    
    // Get
    public Page<Job> handleGetAllJob(Specification<Job> jobSpecification, Pageable jobPageable) {
        return this.jobRepository.findAll(jobSpecification, jobPageable);
    }
    public Job handleGetJobById(Long id) {
        Optional<Job> optionalJob = this.jobRepository.findById(id);
        if (!optionalJob.isPresent()) {
            throw new NoSuchElementException("Job not found");
        }
        return optionalJob.get();
    }
    // Update
    public Job handleUpdateJob(Long id, Job updateJob) {
        Optional<Job> optionalJob = this.jobRepository.findById(id);
        if (!optionalJob.isPresent()) {
            throw new NoSuchElementException("Job not found");
        }
        Job currentJob = optionalJob.get();
        BeanUtils.copyProperties(updateJob, currentJob,"id","createdAt","createdBy","skills");
        // Valid skill
        if (updateJob.getSkills() != null) {
            List<Skill> listSkills = this.skillService.handleValidSkills(updateJob.getSkills());
            List<Long> existIds = currentJob.getSkills().stream()
            .map(Skill::getId)
            .toList();

            listSkills.stream()
            .filter(skill -> !existIds.contains(skill.getId()))
            .forEach(skill -> currentJob.getSkills().add(skill));
        }

        Job created = this.jobRepository.save(currentJob);
        return created;
    }
    // Delete
    public void handleDeleteJob(Long id) {
        Optional<Job> optionalJob = this.jobRepository.findById(id);
        if (!optionalJob.isPresent()) {
            throw new NoSuchElementException("Job not found");
        }
        if (optionalJob.isPresent()) {
            // this.resumeService.deleteResumeByJob(optionalJob.get());
            List<Resume> currentList = this.resumeRepository.findByJob(optionalJob.get());
            this.resumeRepository.deleteAll(currentList);
        }
        // Job currentJob = optionalJob.get();
        // currentJob.getSkills().forEach(skill -> skill.getJobs().remove(currentJob));
        this.jobRepository.delete(optionalJob.get());
    }

    public Boolean handleValidJob(Long id) {
        Optional<Job> optionalJob = this.jobRepository.findById(id);
        return optionalJob.isPresent();
    }

    public void handleDeleteJobByCompany(Company company) {
        this.jobRepository.deleteAll(this.jobRepository.findByCompany(company));
    }
}
