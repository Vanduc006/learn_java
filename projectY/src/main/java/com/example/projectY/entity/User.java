package com.example.projectY.entity;

import java.time.Instant;

import com.example.projectY.utils.SecurityUtil;
import com.example.projectY.utils.constants.GenerEnum;

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

    // @NotBlank(message = "Invalid compnay id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id") // foreign key -> companies.id
    private Company company; // id
    
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
