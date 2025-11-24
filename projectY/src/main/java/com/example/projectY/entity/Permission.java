package com.example.projectY.entity;

import java.time.Instant;
import java.util.List;

import com.example.projectY.utils.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@Table(name = "permissons")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Invalid permisson name")
    private String name;
    private String apiPath;
    private String method;
    private String moudle;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JsonIgnoreProperties(value = {"permissons"})
    // @JoinTable(name = "permisson_role", 
    // joinColumns = @JoinColumn(name = "permisson_id"), 
    // inverseJoinColumns = @JoinColumn(name = "role_id"))
    // private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles;

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
