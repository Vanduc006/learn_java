package com.example.projectY.controller.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.projectY.entity.ApiRespone;
import com.example.projectY.entity.ResponeStatus;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiRespone<?>> handleNoElement(NoSuchElementException ex) {
        ResponeStatus status = new ResponeStatus(HttpStatus.INTERNAL_SERVER_ERROR, "No such element");
        return ResponseEntity.internalServerError().body(new ApiRespone<>(status, null, LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiRespone<?>> handleInvalidArgument(Exception ex) {
        ResponeStatus status = new ResponeStatus(HttpStatus.INTERNAL_SERVER_ERROR, "An error occured");
        return ResponseEntity.internalServerError().body(new ApiRespone<>(status, null, LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiRespone<String>> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        ResponeStatus status = new ResponeStatus(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid argument");
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ":" + error.getDefaultMessage())
        .collect(Collectors.toList());

        String error = String.join("; ", errorList);

        return ResponseEntity.badRequest().body(new ApiRespone<String>(status, error, LocalDateTime.now()));
    }
}
