package com.turing.javaproject.service;

import com.turing.javaproject.entity.User;
import com.turing.javaproject.rest.dto.request.NewUserRequest;
import com.turing.javaproject.rest.dto.request.UpdateUserRequest;
import com.turing.javaproject.rest.dto.response.NewUserResponse;
import com.turing.javaproject.rest.dto.response.UserAllFields;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserAllFields save(UpdateUserRequest user);
    NewUserResponse add(NewUserRequest user);
    UserAllFields getById(Long id);
    UserAllFields getByUsername(String username);
    UserAllFields getByEmail(String email);
    List<UserAllFields> getAll();

    void delete(Long id);
}
