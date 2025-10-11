package com.example.projectY.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectY.entity.User;
// import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    // @Autowired
    @Override
    public Optional<User> findById(Long id);
}
