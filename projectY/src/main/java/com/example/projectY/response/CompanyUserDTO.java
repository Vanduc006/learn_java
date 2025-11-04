package com.example.projectY.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CompanyUserDTO {
    private Long id;
    private String name;
    // private ResCreateUserDTO createUserDTO;

    public CompanyUserDTO() {};

}
