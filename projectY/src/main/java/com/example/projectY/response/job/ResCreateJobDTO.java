package com.example.projectY.response.job;

import java.time.Instant;
import java.util.List;

import com.example.projectY.response.ResGetSkillDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResCreateJobDTO {
    private Long id;
    private String name;
    private String location;
    private double salary;
    private int quantity;

    private String description;
    private Instant startDate;
    private Instant endDate;

    private List<ResGetSkillDTO> skills;
    
    private Instant createdAt;
    private String createdBy;

    public ResCreateJobDTO() {}
}
