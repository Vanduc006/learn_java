package com.example.projectY.response;

import java.time.Instant;

import com.example.projectY.utils.constants.GenerEnum;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResCreateUserDTO {
    private Long id;
    private String username;
    private String email;
    private Integer age;
    private GenerEnum gender; // enum
    private String address;

    private Instant createdAt;
    private String createdBy;
    private CompanyUserDTO companyUser;

    public ResCreateUserDTO() {};

}
