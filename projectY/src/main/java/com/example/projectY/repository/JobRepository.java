package com.example.projectY.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.projectY.entity.Company;
import com.example.projectY.entity.Job;
import com.example.projectY.entity.Skill;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job>{
    public Optional<Job> findById(Long id);
    public List<Job> findByCompany(Company company);
    public List<Job> findBySkillsIn(List<Skill> skills);
}
