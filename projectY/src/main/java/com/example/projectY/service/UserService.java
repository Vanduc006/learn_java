package com.example.projectY.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectY.entity.User;
import com.example.projectY.repository.UserRepository;

public interface UserService {
    public List<User> getAllUser(); // Read

    public User getUserById(Long id); // Read

    public User updateUser(Long id, User updateUser); // Update

    public User createUser(User newUser); // Create

    public String deleteUser(Long id);


}
