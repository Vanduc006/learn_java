package com.example.projectY.response.resume;

import java.time.Instant;

import com.example.projectY.utils.constants.StatusEnum;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResGetResumeDTO {
    private Long id;
    private String email;
    private String url;
    private StatusEnum status;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    private UserResumeDTO user;
    private JobResumeDTO job;

    public ResGetResumeDTO() {}

    @Getter
    @Setter
    @Data
    public static class UserResumeDTO {
        private Long id;
        private String username;

        public UserResumeDTO() {}
    }

    @Getter
    @Setter
    @Data
    public static class JobResumeDTO {
        private Long id;
        private String name;

        public JobResumeDTO() {}
    }

}
