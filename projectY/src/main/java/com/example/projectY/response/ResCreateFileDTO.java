package com.example.projectY.response;

import java.time.Instant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResCreateFileDTO {
    private String fileName;
    private Instant createdAt;
    public ResCreateFileDTO() {}
}
