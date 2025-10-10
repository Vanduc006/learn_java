package com.example.demo.repostories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Page<User> findById(Long id, Pageable pageable);
	boolean existsByEmail(String email);
}
