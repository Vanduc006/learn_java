package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entities.User;

public interface UserService {
    public User createUser(User user);

	public List<User> getAllUsers();

	public Page<User> getUserById(Long id, Pageable pageable);

	public User updateUser(Long id, User updatedUser);

	public void deleteUser(Long id);
}
