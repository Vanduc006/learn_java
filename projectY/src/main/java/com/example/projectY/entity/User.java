package com.example.projectY.entity;

import java.time.Instant;
import java.util.List;

import com.example.projectY.utils.SecurityUtil;
import com.example.projectY.utils.constants.GenerEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Data
public class User { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Invalid name")
    private String username;

    // @NotBlank(message = "Blank")
    @Email
    // @Pattern(Flag = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")
    private String email;

    @NotBlank(message = "Invalid password")
    private String password;

    private Integer age;
    @Enumerated(EnumType.STRING)
    private GenerEnum gender; // enum
    private String address;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;
    
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    // 1 company have n user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id") // foreign key -> companies.id
    private Company company; // id

    // 1 user have n resume
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Resume> resumes;
    
    // 1 role have n user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    // @JsonIgnore
    private Role role;

    public User() {}

    // public User(Long id, @NotBlank(message = "Invalid name") String username, @Email String email,
    //         @NotBlank(message = "Invalid password") String password, int age, String gender, String address,
    //         String refreshToken, Instant createdAt, Instant updatedAt, String createdBy, String updatedBy) {
    //     this.id = id;
    //     this.username = username;
    //     this.email = email;
    //     this.password = password;
    //     this.age = age;
    //     this.gender = gender;
    //     this.address = address;
    //     this.refreshToken = refreshToken;
    //     this.createdAt = createdAt;
    //     this.updatedAt = updatedAt;
    //     this.createdBy = createdBy;
    //     this.updatedBy = updatedBy;
    // }

    // @PrePersist
    // public void handleBeforeCreated() {
    //     this.createdAt = Instant.now();
    //     this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ? 
    //     SecurityUtil.getCurrentUserLogin().get() : "anon";
    // }

    @PreUpdate
    public void handleBeforeUpdated() {
        System.out.println("update");
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ? 
        SecurityUtil.getCurrentUserLogin().get() : "anon";
    }
    

}
