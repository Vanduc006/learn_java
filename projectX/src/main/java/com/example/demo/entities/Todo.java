/*
 * @Author: Nguyen Van Duc (Berri)
 * @Date: 2025-10-01 11:45:08
 */


package com.example.demo.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String username;
    Boolean isDone;

    public Todo() {}

    public Todo(String username, Boolean isDone) {
        // this.id = id;
        this.username = username;
        this.isDone = isDone;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Boolean isDone() {
        return isDone;
    }
    public void setDone(Boolean isDone) {
        this.isDone = isDone;
    }


}
