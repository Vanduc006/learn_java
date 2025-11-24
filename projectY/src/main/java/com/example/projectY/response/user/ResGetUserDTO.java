package com.example.projectY.response.user;

import java.time.Instant;

import com.example.projectY.utils.constants.GenerEnum;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResGetUserDTO {
    private Long id;
    private String username;
    private String email;
    private Integer age;
    private GenerEnum gender; // enum
    private String address;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    private CompanyUserDTO companyUser;

    private RoleUserDTO roleUser;

    public ResGetUserDTO() {};

}
