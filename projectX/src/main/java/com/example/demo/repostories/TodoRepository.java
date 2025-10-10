/*
 * @Author: Nguyen Van Duc (Berri)
 * @Date: 2025-10-01 21:41:09
 */

package com.example.demo.repostories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
    // List<Todo> findByUsername(String username);
    Page<Todo> findByUsername(String username, Pageable pageableTodo);
    Boolean existsByUsername(String username);
}
