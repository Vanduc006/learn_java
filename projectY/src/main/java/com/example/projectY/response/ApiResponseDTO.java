package com.example.projectY.response;

import java.time.LocalDateTime;

// import org.springframework.http.HttpStatus;

public class ApiResponseDTO <T> {
    private ResponseStatusDTO status;
    private T data;
    private LocalDateTime timeStamp = LocalDateTime.now();

    public ApiResponseDTO() {}

    public ApiResponseDTO(ResponseStatusDTO status, T data, LocalDateTime timeStamp) {
        this.status = status;
        this.data = data;
        this.timeStamp = timeStamp;
    }

    public ResponseStatusDTO getStatus() {
        return status;
    }
    public void setStatus(ResponseStatusDTO status) {
        this.status = status;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public LocalDateTime timeStamp() {
        return timeStamp;
    }
    public void setMessage(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    

    
}
