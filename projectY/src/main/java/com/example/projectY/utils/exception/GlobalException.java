package com.example.projectY.utils.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.projectY.response.ApiResponseDTO;
import com.example.projectY.response.ResponseStatusDTO;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleNoElement(NoSuchElementException ex) {
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.INTERNAL_SERVER_ERROR, "No such element");
        return ResponseEntity.internalServerError().body(new ApiResponseDTO<>(status, null, LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<?>> handleInvalidArgument(Exception ex) {
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.internalServerError().body(new ApiResponseDTO<>(status, null, LocalDateTime.now()));
    }

 

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        ResponseStatusDTO status = new ResponseStatusDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid argument");
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ":" + error.getDefaultMessage())
        .collect(Collectors.toList());

        String error = String.join("; ", errorList);

        return ResponseEntity.badRequest().body(new ApiResponseDTO<String>(status, error, LocalDateTime.now()));
    }
}
