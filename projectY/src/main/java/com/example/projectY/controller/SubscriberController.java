package com.example.projectY.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectY.entity.Subscriber;
import com.example.projectY.response.ApiResponseDTO;
import com.example.projectY.response.ResponseStatusDTO;
import com.example.projectY.service.SubscriberService;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class SubscriberController {
    @Autowired
    private SubscriberService subscriberService;

    @GetMapping("/subscribers")
    public ResponseEntity<ApiResponseDTO<?>> getSubscribers(
        @Filter Specification<Subscriber> subscriberSpecification,
        Pageable subscriberPageable
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ApiResponseDTO<>(
            new ResponseStatusDTO(HttpStatus.CREATED, "Get all subscribers"),
            this.subscriberService.handleGetAllSubscribers(subscriberPageable, subscriberSpecification),
            LocalDateTime.now()
        ));
    }

    @GetMapping("/subscribers/{id}")
    public ResponseEntity<ApiResponseDTO<?>> getSubscriberById(
        @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok()
        .body(new ApiResponseDTO<>(
            new ResponseStatusDTO(HttpStatus.OK, "Get subscriber"),
            this.subscriberService.handleGetSubscriberById(id),
            LocalDateTime.now()
        ));
    }

    @PostMapping("/subscribers")
    public ResponseEntity<ApiResponseDTO<?>> createSubscriber(
        @Valid @RequestBody Subscriber newSubscriber
    ) {
        return ResponseEntity.ok()
        .body(new ApiResponseDTO<>(
            new ResponseStatusDTO(HttpStatus.OK, "Create subscriber"),
            this.subscriberService.handleCreateSubsriber(newSubscriber),
            LocalDateTime.now()
        ));
    }

    @PutMapping("/subscribers/{id}")
    public ResponseEntity<ApiResponseDTO<?>> updateSubscriber(
        @PathVariable(name = "id") Long id,
        @RequestBody Subscriber updateSubscriber
    ) {
        return ResponseEntity.ok()
        .body(new ApiResponseDTO<>(
            new ResponseStatusDTO(HttpStatus.OK,"Update subscriber"),
            this.subscriberService.handleUpdateSubscriber(id, updateSubscriber),
            LocalDateTime.now()
        ));
    }
    

    @DeleteMapping("/subscribers/{id}")
    public ResponseEntity<ApiResponseDTO<?>> deleteSubsriber(
        @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok()
        .body(new ApiResponseDTO<>(
            new ResponseStatusDTO(HttpStatus.OK, "Delete subscriber"),
            null,
            LocalDateTime.now()
        ));
    }
}

