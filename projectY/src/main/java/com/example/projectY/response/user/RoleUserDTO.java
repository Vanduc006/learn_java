package com.example.projectY.response.user;

import java.util.List;

import com.example.projectY.entity.Permission;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RoleUserDTO {
    private Long id;
    private String name;
    private List<Permission> permissions;

    public RoleUserDTO() {}
}
