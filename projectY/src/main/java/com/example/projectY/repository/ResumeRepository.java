package com.example.projectY.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.projectY.entity.Job;
import com.example.projectY.entity.Resume;
import com.example.projectY.entity.User;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume>{
    public Optional<Resume> findById(Long id);
    public List<Resume> findByUser(User user);
    public List<Resume> findByJob(Job job);
}
