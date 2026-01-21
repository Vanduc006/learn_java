package com.example.projectY.response;

import java.util.List;

import com.example.projectY.entity.Permission;
import com.example.projectY.entity.Role;
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
    @JsonProperty("user")
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
        private RoleUserLoginDTO role;
        // private Role role;

        public UserLoginDTO() {};

        @Getter
        @Setter
        @Data
        public static class RoleUserLoginDTO {
            private Long id;
            private String name;
            private List<PermissionRoleUserLoginDTO> permissions;

            public RoleUserLoginDTO() {}

            @Setter
            @Getter
            @Data
            public static class PermissionRoleUserLoginDTO {
                private Long id;
                private String name;
                private String apiPath;
                private String method;
                private String moudle;

                public PermissionRoleUserLoginDTO() {}
            }
        }


    }

    @Getter
    @Setter
    @Data
    public static class GetUserDTO {
        private UserLoginDTO user;
        public GetUserDTO() {};
    }

    @Getter
    @Setter
    @Data
    public static class UserInsideToken {
        private Long id;
        private String username;
        private String email;

        public UserInsideToken() {};
    }

}
