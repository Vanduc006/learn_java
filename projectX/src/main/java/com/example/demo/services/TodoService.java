package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entities.Todo;

public interface TodoService {
    public Todo handleCreateTodo(Todo todo);

    public List<Todo> handleGetAllTodo();

    public Page<Todo> handleGetUserTodo(
        // Long id,
        String name,
        Pageable pageableTodo
        );

    public void handlePartialUpdateTodo(Long id,Todo updateTodo );

    public void handleUpdateTodo(Long id, Todo updateTodo);

    public void handleDeleteTodo(Long id);

}
