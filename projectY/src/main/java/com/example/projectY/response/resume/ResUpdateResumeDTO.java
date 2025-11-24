package com.example.projectY.response.resume;

import java.time.Instant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResUpdateResumeDTO {
    private Instant updatedAt;
    private String updatedBy;

    public ResUpdateResumeDTO() {}
}
