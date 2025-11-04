package com.example.projectY.response;

import java.time.Instant;

import com.example.projectY.utils.constants.GenerEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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

    public ResGetUserDTO() {};

}
