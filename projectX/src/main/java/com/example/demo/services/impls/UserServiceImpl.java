package com.example.demo.services.impls;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repostories.UserRepository;
import com.example.demo.services.UserService;

// import vn.hoidanit.todo.entity.User;
// import vn.hoidanit.todo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	// public UserService(UserRepository userRepository) {
	// 	this.userRepository = userRepository;
	// }
	public User createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Page<User> getUserById(Long id, Pageable pageable) {
		// if()
		// return userRepository.findById(id);
		// Optional<User> optionalUser = this.userRepository.findById(id);
		Page<User> pageUser = this.userRepository.findById(id, pageable);
		// if (!optionalUser.isPresent()) {
		// 	throw new NoSuchElementException("User not found");
		// }
		return pageUser;
	}

	public User updateUser(Long id, User updatedUser) {
		return userRepository.findById(id).map(user -> {
			user.setName(updatedUser.getName());
			user.setEmail(updatedUser.getEmail());
			return userRepository.save(user);
		}).orElseThrow(() -> new NoSuchElementException("User not found"));
	}

	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new NoSuchElementException("User not found");
		}
		userRepository.deleteById(id);
	}

	
}