package com.example.projectY.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.projectY.entity.Subscriber;
import java.util.List;
import java.util.Optional;


@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>, JpaSpecificationExecutor<Subscriber>{
    @Override
    public Optional<Subscriber> findById(Long id);

    public Boolean existsByEmail(String email);
}
