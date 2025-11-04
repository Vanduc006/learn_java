package com.example.projectY.response;

import org.springframework.http.HttpStatus;

public class ResponeStatusDTO {
    private HttpStatus statusCode;
    private String statusMessage;

    public ResponeStatusDTO(HttpStatus statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    

    
}
