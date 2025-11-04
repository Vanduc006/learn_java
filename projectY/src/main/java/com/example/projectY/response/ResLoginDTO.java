package com.example.projectY.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResLoginDTO {
    @JsonProperty("access_token")
    private String accessToken;
    private UserLoginDTO userLoginDTO;

    public ResLoginDTO(String accessToken, UserLoginDTO userLoginDTO) {
        this.accessToken = accessToken;
        this.userLoginDTO = userLoginDTO;
    }

    @Getter
    @Setter
    @Data
    public static class UserLoginDTO {
        private Long id;
        private String username;
        private String email;

        public UserLoginDTO() {};

    }

    @Getter
    @Setter
    @Data
    public static class GetUserDTO {
        private UserLoginDTO user;
        public GetUserDTO() {};
    }

    
}
