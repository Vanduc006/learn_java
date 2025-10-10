package com.example.demo.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entities.ApiRespone;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import com.example.demo.services.impls.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import vn.hoidanit.todo.entity.User;
// import vn.hoidanit.todo.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {
	@Autowired
	private ObjectMapper om;

	@Autowired
	private UserService userService;

	// public UserController(UserService userService) {
	// this.userService = userService;
	// }

	
	@GetMapping("/mapper")
	public ResponseEntity<ApiRespone<User>> getString() throws Exception {
		String json = """
				{
					"name" : "duc",
					"email" : "duc@abc.com"
				}
						""";
		User user = om.readValue(json, User.class);
		User test = new User(1L, "chaochao", "bear@abc.com");
		String testJson = om.writeValueAsString(test);

		return ResponseEntity.ok()
				.body(new ApiRespone<User>(HttpStatus.CREATED, "Test Object mapper", user, null, LocalDateTime.now()));
	}

	@PostMapping("/users")
	public ResponseEntity<ApiRespone<User>> createUser(@Valid @RequestBody User user) {
		User created = userService.createUser(user);
		// String json = """
		// {
		// "name" :
		// }
		// """;

		// json -> object
		return ResponseEntity.status(HttpStatus.CREATED).body(
				new ApiRespone<User>(HttpStatus.CREATED, "Create user successful", created, null, LocalDateTime.now()));
	}

	@GetMapping("/users")
	public ResponseEntity<ApiRespone<List<User>>> getAllUsers() {
		return ResponseEntity.ok().body(new ApiRespone<List<User>>(HttpStatus.OK, "Get all users",
				this.userService.getAllUsers(), null, LocalDateTime.now()));
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiRespone<Page<User>>> getUserById(
		@PathVariable Long id,
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size,
		@RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
		@RequestParam(name = "asc", defaultValue = "true", required = false) boolean asc
	) {
		Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<User> pageUser = this.userService.getUserById(id, pageable);
		if (pageUser.isEmpty()) {
			// return ResponseEntity.noContent().build();
			return ResponseEntity.internalServerError().build();
		} 
		return ResponseEntity.ok().body(new ApiRespone<>(HttpStatus.OK, "Get user by Id", pageUser, null, LocalDateTime.now()));
		// return userService.getUserById(id,pageable)
		// .map(user -> ResponseEntity.ok().body(new ApiRespone<Page<User>>(HttpStatus.OK,"Get user by Id",user,null,LocalDateTime.now())));
		// .orElse(ResponseEntity.noContent().build());
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<ApiRespone<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
		User updated = userService.updateUser(id, user);
		return ResponseEntity
				.ok(new ApiRespone<User>(HttpStatus.OK, "Update user", updated, null, LocalDateTime.now()));
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiRespone<Void>> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiRespone<Void>(HttpStatus.OK, "Delete user", null, null, LocalDateTime.now()));
	}
}
