package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demo.entities.Todo;
import com.example.demo.repostories.TodoRepository;
import com.example.demo.services.impls.TodoServiceImpl;

// format nameing method <methodBeingTested>_should<Expect>_when<condition>

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock // fake
    private TodoRepository todoRepository; // main element for test

    @InjectMocks // TodoRepository (fake) + TodoService
    private TodoServiceImpl todoService;

    // cause we need this constructor
    // public TodoService(TodoRepository todoRepository) {
    // this.todoRepository = todoRepository;
    // }

    @Test
    public void createTodo_shouldThrowException_whenUsernameExist() {
        // arrange
        Todo inputTodo = new Todo("chaochao", true);

        when(this.todoRepository.existsByUsername(inputTodo.getUsername())).thenReturn(true);
        // when(this.todoRepository.save(any())).thenThrow(new IllegalArgumentException("Username already exist"));

        // act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            this.todoService.handleCreateTodo(inputTodo);
        });

        // assert
        assertEquals("Username already exist", ex.getMessage());
    }

    @Test
    public void createTodo_shouldReturnTodo_whenUsernameNotExist() {
        // arrange
        Todo inputTodo = new Todo("boby", true);
        Todo outputTodo = new Todo("boby", true);

        when(this.todoRepository.existsByUsername(inputTodo.getUsername())).thenReturn(false);

        when(this.todoRepository.save(any())).thenReturn(outputTodo); // any is input paramsmeter
        // act
        Todo result = this.todoService.handleCreateTodo(inputTodo);
        // assert
        assertEquals("boby", result.getUsername());
    }

    @Test 
    public void getAllTodo_shouldReturnAllTodo() {
        // arrange
        List<Todo> outputTodo = new ArrayList<>();
        outputTodo.add(new Todo("chaochao",true));
        outputTodo.add(new Todo("boby",false));

        when(this.todoRepository.findAll()).thenReturn(outputTodo);
        // act
        List<Todo> result = this.todoService.handleGetAllTodo();
        assertEquals(outputTodo.size(), result.size());

        // can check more than one :))
        assertEquals("chaochao", result.get(0).getUsername());
    }

    @Test
    public void getUserTodo_shouldReturnListTodo_whenParamsValid() {
        // arrange
        String inputUsername = "chaochao";
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").ascending());

        List<Todo> outputTodo = new ArrayList<>();
        outputTodo.add(new Todo("chaochao",true));
        outputTodo.add(new Todo("chaochao",true));
        Page<Todo> mockPage = new PageImpl<>(outputTodo, pageable,outputTodo.size());

        when(this.todoRepository.findByUsername(inputUsername, pageable)).thenReturn(mockPage);
        // act
        Page<Todo> result = this.todoService.handleGetUserTodo(inputUsername, pageable);

        // assert
        assertEquals(mockPage, result);
    }

    @Test
    public void updatePartialTodo_shouldPartialUpdate_whenIdValid() {
        // arrange
        Long inputId = 1L;
        Todo exstingTodo = new Todo("boby",false);
        Todo paritialUpdate = new Todo("chaochao",null);

        when(todoRepository.findById(inputId)).thenReturn(Optional.of(exstingTodo));
        // act
        this.todoService.handlePartialUpdateTodo(inputId, paritialUpdate);

        // assert
        assertEquals("chaochao", exstingTodo.getUsername());
        assertFalse(exstingTodo.isDone());
        verify(todoRepository, times(1)).save(exstingTodo); 
        // check if save update
        
    }

    @Test
    public void updateTodo_shouldUpdateTodo_WhenIdValid() {
        // arrange
        Long inputId = 1L;
        Todo existingTodo = new Todo("chaochao",true);
        existingTodo.setId(inputId);
        Todo update = new Todo("kaikai",false);
        update.setId(999L);

        when(this.todoRepository.findById(inputId)).thenReturn(Optional.of(existingTodo));
        // act
        this.todoService.handleUpdateTodo(inputId, update);

        // assert
        assertEquals("kaikai", existingTodo.getUsername());
        assertEquals(inputId, existingTodo.getId());
        verify(todoRepository).save(existingTodo);
    }

    @Test
    public void deleteTodo_shouldThrowException_whenIdInvalid() {
        // arrange
        Long id = 1L;
        // Todo existingTodo = new Todo("chaochao",true);
        // existingTodo.setId(100L);

        when(this.todoRepository.findById(id)).thenReturn(Optional.empty());

        // act
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> {
            this.todoService.handleDeleteTodo(id);
        });

        // assert
        assertEquals("Todo not found", ex.getMessage());
    }

    @Test
    public void deleteTodo_shouldDeleteTodo_whenIdValid() {
        // arrange
        Long id = 1L;
        Todo existingTodo = new Todo("chaochao",true);
        existingTodo.setId(1L);

        when(this.todoRepository.findById(id)).thenReturn(Optional.of(existingTodo));
        // act
        this.todoService.handleDeleteTodo(id);

        // assert
        verify(todoRepository).deleteById(id);


    }

}
