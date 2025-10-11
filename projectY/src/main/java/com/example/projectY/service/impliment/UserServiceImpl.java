package com.example.projectY.service.impliment;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectY.entity.User;
import com.example.projectY.repository.UserRepository;
import com.example.projectY.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    @Autowired UserRepository userRepository;

    public List<User> getAllUser() { // Read
        return this.userRepository.findAll();   
    }

    public User createUser(User newUser) { // Create
        return this.userRepository.save(newUser);
    }
    

    public User getUserById(Long id) { // Read
        // List<User>
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException("User not found");
        }
        return optionalUser.get();
        // return optionalUser.map(user -> user);
    }

    public User updateUser(Long id, User updateUser) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException("User not found");
        }
        User currentUser = optionalUser.get();
        if (updateUser.getUsername() != "") {
            currentUser.setUsername(updateUser.getUsername());
        }

        if (updateUser.getEmail() != "") {
            currentUser.setEmail(updateUser.getEmail());
        }

        if (updateUser.getPassword() != "") {
            currentUser.setPassword(updateUser.getPassword());
        }

        return currentUser;
    }

    public String deleteUser(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException("User not found");
        }
        User currentUser = optionalUser.get();
        this.userRepository.deleteById(currentUser.getId());
        return "Delete user with Id" + currentUser.getId();
        // return this.userRepository.deleteById(id);
    }
}
