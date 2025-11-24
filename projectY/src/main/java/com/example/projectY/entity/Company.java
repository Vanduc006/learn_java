package com.example.projectY.entity;

import java.time.Instant;
import java.util.List;


import com.example.projectY.utils.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "companies")
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Blank name")
    private String name; // VARCHAR255

    @Column(columnDefinition = "MEDIUMTEXT") // MORE
    private String description;
    private String address;
    private String logo;
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    // 1 company have n user
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;

    // 1 company have n job
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Job> jobs;

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
