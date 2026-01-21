package com.example.projectY.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResSubscriberEmailDTO {
    private String email;
    private String name;
    private List<ResJobEmailDTO> jobs;
}
