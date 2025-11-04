package com.example.projectY.utils.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.projectY.response.ApiResponeDTO;
import com.example.projectY.response.ResponeStatusDTO;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponeDTO<?>> handleNoElement(NoSuchElementException ex) {
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.INTERNAL_SERVER_ERROR, "No such element");
        return ResponseEntity.internalServerError().body(new ApiResponeDTO<>(status, null, LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponeDTO<?>> handleInvalidArgument(Exception ex) {
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.internalServerError().body(new ApiResponeDTO<>(status, null, LocalDateTime.now()));
    }

 

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponeDTO<String>> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        ResponeStatusDTO status = new ResponeStatusDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid argument");
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ":" + error.getDefaultMessage())
        .collect(Collectors.toList());

        String error = String.join("; ", errorList);

        return ResponseEntity.badRequest().body(new ApiResponeDTO<String>(status, error, LocalDateTime.now()));
    }
}
