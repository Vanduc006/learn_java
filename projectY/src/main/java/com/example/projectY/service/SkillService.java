package com.example.projectY.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.projectY.entity.Skill;

public interface SkillService {
    public Skill handleCreateSkill(Skill newSkill); // Create

    public List<Skill> handleGetAllSkill(); // Read

    public Skill handleUpdateSkill(Long id, Skill updateSkill); // Update

    public void handleDeleteSkill(Long id); // Delete

    public Skill handleGetSkillById(Long id);

    public Page<Skill> handleFilterSkill(Specification<Skill> skillSpecification, Pageable skillPageable);

    public List<Skill> handleValidSkills(List<Skill> listSkills);
}
