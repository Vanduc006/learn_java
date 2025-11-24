package com.example.projectY.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.projectY.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {
    public Optional<Skill> findById(Long id);
    public Optional<Skill> findByName(String name);
    public List<Skill> findByIdIn(List<Long> id);
}
