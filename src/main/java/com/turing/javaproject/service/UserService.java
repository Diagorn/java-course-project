package com.turing.javaproject.service;

import com.turing.javaproject.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User save(User user);
    User add(User user);
    User getById(Long id);
    User getByUsername(String username);
    User getByEmail(String email);
    List<User> getAll();

    void delete(Long id);
}
