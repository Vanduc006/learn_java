package com.example.projectY.entity;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.example.projectY.utils.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private Boolean active;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    // @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    // @JsonIgnore
    // private List<Permission> permissons;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"roles"})
    @JoinTable(name = "permisson_role", 
    joinColumns = @JoinColumn(name = "role_id"), 
    inverseJoinColumns = @JoinColumn(name = "permisson_id"))
    private List<Permission> permissions;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;

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
