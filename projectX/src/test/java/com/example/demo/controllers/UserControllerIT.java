package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.IntegrationTest;
import com.example.demo.entities.User;
import com.example.demo.repostories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@IntegrationTest
@AutoConfigureMockMvc
@Transactional // rollback when test done
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        this.userRepository.deleteAll();
    }

    @Test
    public void createUser_shouldReturnUser_whenParamsValid() throws Exception {
        // arrange
        // this.userRepository.deleteAll();
        User inputUser = new User(null,"duc","abc@gg.com");

        // act
        String result = mockMvc.perform(
            post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(inputUser)) // body
        ).andExpect(status().isCreated()) // expect 201
        .andReturn().getResponse().getContentAsString()
        ; 

        // assert
        User outputUser = objectMapper.readValue(result, User.class);
        assertEquals(inputUser.getName(), outputUser.getName());
    }

    @Test
    public void getAllUser_shouldReturnAllUser() throws Exception{
        // arrange
        // this.userRepository.deleteAll();
        User user1 = new User(null,"duc","abc@kk.com");
        User user2 = new User(null,"duc2","abc@gg.com");
        List<User> data = List.of(user1,user2);

        this.userRepository.saveAll(data);
        // action
        String result = mockMvc.perform(
            get("/users")
        ).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

        // assert
        List<User> outputList = objectMapper.readValue(result, new TypeReference<List<User>>() {});

        assertEquals(data.size(),outputList.size());
        assertEquals(data.get(0).getEmail(), outputList.get(0).getEmail());
        // verify(this.userRepository).findAll();
    }

    @Test
    public void getUser_shouldReturnUser_whenIdValid() throws Exception{
        // arrange
        // this.userRepository.deleteAll();
        User user = new User(null,"duc","abc@kk.com");
        User savedUser = this.userRepository.save(user);

        // act
        String result = mockMvc.perform(
            get("/users/"+savedUser.getId())
        ).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

        // assert
        User outputUser = objectMapper.readValue(result, User.class);
        assertEquals(savedUser.getName(), outputUser.getName());
        assertEquals(savedUser.getId(), outputUser.getId());
        
    }

    @Test
    public void getUser_shouldThrowException_whenIdInvalid() throws Exception{
        // arrange
        // this.userRepository.deleteAll();
        User savedUser = this.userRepository.saveAndFlush(new User(null,"duc","duc@abc.com"));

        // act
        // NoSuchElementException result = mockMvc.perform(
        //     get("/users/"+100L)
        // ).andExpect(status().isNotFound())
        // .andReturn().getResponse();

        mockMvc.perform(
            get("/users/{id}", savedUser.getId()+1L)
        ).andExpect(status().isNotFound());


        // System.out.println(result);
        // assert
    }

    @Test
    public void updateUser_shouldUpdateUser_WhenIdValid() throws Exception{
        // arrange
        // this.userRepository.deleteAll();
        User user = new User(null,"duc","abc@kk.com");
        User savedUser = this.userRepository.save(user);

        User updateUser = new User(null,"duc2","abc@gg.com");

        // act
        String result = mockMvc.perform(
            put("/users/"+savedUser.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(updateUser))
        ).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

        // 
        User ouputUser = objectMapper.readValue(result, User.class);

        assertEquals(updateUser.getEmail(), ouputUser.getEmail());

    }

    @Test
    public void deleteUser_shouldVoid_whenIdValid() throws Exception{
        // arrange
        // this.userRepository.deleteAll();
        User savedUser = this.userRepository.save(new User(null,"duc","duc@abc.com"));

        // act
        String result = mockMvc.perform(
            delete("/users/"+savedUser.getId())
        ).andExpect(status().isNoContent())
        .andReturn().getResponse().getContentAsString();

        // assert
        assertEquals("", result);
    }


}
