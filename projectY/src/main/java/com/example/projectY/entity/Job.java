package com.example.projectY.entity;

import java.time.Instant;
import java.util.List;

import com.example.projectY.utils.SecurityUtil;
import com.example.projectY.utils.constants.LevelEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jobs")
@Getter
@Setter
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Invalid job name")
    private String name;

    private String location;
    private double salary;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private LevelEnum level;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private Instant startDate;
    private Instant endDate;
    private Boolean active;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    // 1 company have n job
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    // n job have n skill
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"jobs"})
    @JoinTable(name = "job_skill",
    joinColumns = @JoinColumn(name = "job_id"), 
    inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills;

    // 1 job have n resume
    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Resume> resumes;

    public Job() {}

    @PrePersist
    public void handleBeforeCreated() {
        this.createdAt = Instant.now();
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ? 
        SecurityUtil.getCurrentUserLogin().get() : "anon";
    }

    @PreUpdate
    public void handleBeforeUpdated() {
        System.out.println("update");
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ? 
        SecurityUtil.getCurrentUserLogin().get() : "anon";
    }
    
}
