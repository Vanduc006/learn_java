package com.example.projectY.response;

import java.time.LocalDateTime;

// import org.springframework.http.HttpStatus;

public class ApiResponeDTO <T> {
    private ResponeStatusDTO status;
    private T data;
    private LocalDateTime timeStamp = LocalDateTime.now();

    public ApiResponeDTO() {}

    public ApiResponeDTO(ResponeStatusDTO status, T data, LocalDateTime timeStamp) {
        this.status = status;
        this.data = data;
        this.timeStamp = timeStamp;
    }

    public ResponeStatusDTO getStatus() {
        return status;
    }
    public void setStatus(ResponeStatusDTO status) {
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
