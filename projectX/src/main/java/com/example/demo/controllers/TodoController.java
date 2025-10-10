/*
 * @Author: Nguyen Van Duc (Berri)
 * @Date: 2025-10-01 20:25:48
 */

package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Todo;
import com.example.demo.services.TodoService;
// import com.example.demo.services.impls.TodoServiceImpl;

@RestController
@CrossOrigin("*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/todos") // should be post mapping
    public ResponseEntity<Todo> create(@RequestBody Todo createTodo) {
        // Todo myTodo = new Todo("duc",true);
        // return newTodo.getUsername();
        return ResponseEntity.status(HttpStatus.CREATED).body(this.todoService.handleCreateTodo(createTodo));
    }

    @GetMapping("/todos/{username}")
    // public ResponseEntity<Optional<Todo>> getTodos(
    public ResponseEntity<Page<Todo>> getUserTodos(
        @PathVariable String username,
        @RequestParam(name="page",defaultValue = "0",required = false) int page,
        @RequestParam(name="size",defaultValue = "5",required = false) int size,
        @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
        @RequestParam(name="asc",defaultValue = "true",required = false) boolean asc
        // @RequestParam(name="id") Long id, @RequestParam(name="username") String username
        ) {
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok().body(this.todoService.handleGetUserTodo(username,pageable));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getTodos() {
        return ResponseEntity.ok().body(this.todoService.handleGetAllTodo());
    }


    @PutMapping("/todos/{id}")
    public ResponseEntity<Void> updateTodo(
        // @RequestParam(name="id") Long id,
        @PathVariable Long id,
        @RequestBody Todo updateTodo
        ) {
        this.todoService.handleUpdateTodo(id,updateTodo);
        return ResponseEntity.ok().body(null);
    }

    @PatchMapping("/todos/{id}")
    public ResponseEntity<Void> updatePartialTodo(
        @PathVariable Long id,
        @RequestBody Todo updateTodo
    ) {
        this.todoService.handlePartialUpdateTodo(id,updateTodo);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodo(
        // @RequestParam(name="id") Long id
        @PathVariable Long id
        ) {
        this.todoService.handleDeleteTodo(id);
        
    }
    // @GetMapping("/get-todo")
    // public String get() {
        
    // }
}
