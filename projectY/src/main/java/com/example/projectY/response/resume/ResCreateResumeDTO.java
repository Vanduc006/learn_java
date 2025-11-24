package com.example.projectY.response.resume;

import java.time.Instant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResCreateResumeDTO {
    private Long id;
    private Instant createdAt;
    private String createdBy;

    public ResCreateResumeDTO() {}
}
