package com.example.projectY.entity;

import java.time.LocalDateTime;

// import org.springframework.http.HttpStatus;

public class ApiRespone <T> {
    private ResponeStatus status;
    private T data;
    private LocalDateTime timeStamp = LocalDateTime.now();

    public ApiRespone() {}

    public ApiRespone(ResponeStatus status, T data, LocalDateTime timeStamp) {
        this.status = status;
        this.data = data;
        this.timeStamp = timeStamp;
    }

    public ResponeStatus getStatus() {
        return status;
    }
    public void setStatus(ResponeStatus status) {
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
