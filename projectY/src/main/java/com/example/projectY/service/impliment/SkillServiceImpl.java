package com.example.projectY.service.impliment;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.projectY.entity.Skill;
import com.example.projectY.repository.SkillRepository;
import com.example.projectY.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService{
    @Autowired 
    private SkillRepository skillRepository;

    public Skill handleCreateSkill(Skill newSkill) {
        this.skillRepository.findByName(newSkill.getName()).ifPresent(skill -> {
            throw new DuplicateKeyException("Skill already exists");
        });
        return this.skillRepository.save(newSkill);
    }

    public List<Skill> handleGetAllSkill() {
        return this.skillRepository.findAll();
    }; // Read

    // public Skill handleGetSkillById();
    public Skill handleUpdateSkill(Long id, Skill updateSkill) {
        Optional<Skill> optionalSkill = this.skillRepository.findById(id);
        if (!optionalSkill.isPresent()) {
            throw new NoSuchElementException("Skill not found");
        }
        this.skillRepository.findByName(updateSkill.getName())
            .ifPresent(skill -> {
                throw new DuplicateKeyException("Skill name already exists");
        });

        Skill currentSkill = optionalSkill.get();
        BeanUtils.copyProperties(updateSkill, currentSkill,"id", "createdAt", "createdBy");
        return this.skillRepository.save(currentSkill);
    } // Update

    public void handleDeleteSkill(Long id) {
        Skill currentSkill = this.skillRepository.findById(id)
        .orElseThrow(() -> new DuplicateKeyException("Skill not found"));
        currentSkill.getJobs().forEach(job -> job.getSkills().remove(currentSkill));
        
        this.skillRepository.delete(currentSkill);
    } // Delete

    public Skill handleGetSkillById(Long id) {
        Skill currentSkill = this.skillRepository.findById(id)
        .orElseThrow(() -> new DuplicateKeyException("Skill not found"));

        return currentSkill;
    }

    public Page<Skill> handleFilterSkill(Specification<Skill> skillSpecification, Pageable skillPageable) {
        Page<Skill> pageSkill = this.skillRepository.findAll(skillSpecification, skillPageable);
        return pageSkill;
    }

    public List<Skill> handleValidSkills(List<Skill> listSkills) {
        List<Skill> listValidSkills = listSkills.stream()
        
        .map(skill -> this.skillRepository.findById(skill.getId()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();

        return listValidSkills;
    }
}
