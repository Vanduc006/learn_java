package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.IntegrationTest;
import com.example.demo.entities.Todo;
import com.example.demo.repostories.TodoRepository;
import com.example.demo.services.TodoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Access;

@IntegrationTest
@AutoConfigureMockMvc
@Transactional
public class TodoControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    public void init() {
        this.todoRepository.deleteAll();
    }

    @Test
    public void getAllTodos_shouldReturnAllTodos() throws Exception {
        // arrrange
        // Todo inputTodo = this.todoRepository.save(new Todo("chaochao",true));
        Todo todo1 = new Todo();
        Todo todo2 = new Todo("chaochao",true);
        List<Todo> list = List.of(todo1, todo2);
        this.todoRepository.saveAll(list);
        // act
        String result = mockMvc.perform(
            get("/todos")
        ).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

        // assert
        List<Todo> listOutput = objectMapper.readValue(result, new TypeReference<List<Todo>>() {
            
        });
        assertEquals(list.size(), listOutput.size());
    }
}
