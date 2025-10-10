
package com.example.demo.services.impls;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Todo;
import com.example.demo.repostories.TodoRepository;
import com.example.demo.services.TodoService;

@Service
public class TodoServiceImpl implements TodoService{
    @Autowired
    private TodoRepository todoRepository;

    public Todo handleCreateTodo(Todo todo) {
        if (this.todoRepository.existsByUsername(todo.getUsername())) {
            throw new IllegalArgumentException("Username already exist");
        }
        Todo createdTodo = this.todoRepository.save(todo);
        return createdTodo;
    }

    public List<Todo> handleGetAllTodo() {
        return this.todoRepository.findAll();
    }

    public Page<Todo> handleGetUserTodo(
        // Long id,
        String name,
        Pageable pageableTodo
        ) {
        // List<Todo> todos = this.todoRepository.findAll();
        // Optional<Todo> todos = this.todoRepository.findById(id);
        Page<Todo> pageTodo = this.todoRepository.findByUsername(name, pageableTodo);
        // get by .findByUsername()
        // return todos;
        return pageTodo;
        
    }

    public void handlePartialUpdateTodo(Long id,Todo updateTodo ) {
        this.todoRepository.findById(id).ifPresent(exsitingTodo -> {
            if(updateTodo.getUsername() != null) {
                exsitingTodo.setUsername(updateTodo.getUsername());
            }
            if(updateTodo.isDone() != null) {
                exsitingTodo.setDone(updateTodo.isDone());
            }
            this.todoRepository.save(exsitingTodo);
        });
    }

    public void handleUpdateTodo(Long id, Todo updateTodo) {
        Optional<Todo> todoOptional = this.todoRepository.findById(id);
        if(!todoOptional.isPresent()) {
            throw new NoSuchElementException("Todo not found");
        }
        Todo currentTodo = todoOptional.get();
        BeanUtils.copyProperties(updateTodo,currentTodo,"id");
        this.todoRepository.save(currentTodo);
    }

    public void handleDeleteTodo(Long id) {
        // this.todoRepository.deleteById(id);
        Optional<Todo> todoOptional = this.todoRepository.findById(id);
        if (!todoOptional.isPresent()) {
            throw new NoSuchElementException("Todo not found");
        }
        Todo currentTodo = todoOptional.get();
        this.todoRepository.deleteById(currentTodo.getId());
    }


    // public void handleGetTodo() {
    //     this.todoRepository.findBy()
    // }
}
 