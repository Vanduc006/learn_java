package com.example.projectY.request;

import jakarta.validation.constraints.NotBlank;

public class ReqLoginDTO {
    
    @NotBlank(message = "Invalid username")
    private String username;
    @NotBlank(message = "Invalid password")
    private String password;

    public ReqLoginDTO() {}
    
    public ReqLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    
}
