package com.example.demo.controllers.errros;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.example.demo.entities.ApiRespone;

@RestControllerAdvice
public class GlobalException {
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ApiRespone<?>> handleNotFound(NoSuchElementException ex) {
    return ResponseEntity.internalServerError().body(new ApiRespone<>(HttpStatus.INTERNAL_SERVER_ERROR,
        "Internal Server Error", null, ex.getMessage(), LocalDateTime.now()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiRespone<?>> handleInvalidArgument(Exception ex) {
    return ResponseEntity.internalServerError().body(
        new ApiRespone<>(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error", null, ex.getMessage(), LocalDateTime.now()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiRespone<String>> handleMethodArgumentException(MethodArgumentNotValidException ex) {
    List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
    .map(error -> error.getField() + ":" + error.getDefaultMessage())
    .collect(Collectors.toList());

    String error = String.join("; ", errorList);

    return ResponseEntity.badRequest().body(new ApiRespone<String>(HttpStatus.BAD_REQUEST,"Invalid argument",null, error,LocalDateTime.now()));
  }
  // @ExceptionHandler(ExceptionIlligal.class)
}
