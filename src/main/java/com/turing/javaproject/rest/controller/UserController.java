package com.turing.javaproject.rest.controller;

import com.turing.javaproject.entity.User;
import com.turing.javaproject.rest.dto.request.NewUserRequest;
import com.turing.javaproject.rest.dto.request.UpdateUserRequest;
import com.turing.javaproject.rest.dto.response.NewUserResponse;
import com.turing.javaproject.rest.dto.response.UserAllFields;
import com.turing.javaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<UserAllFields>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserAllFields> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(id));
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserAllFields> getByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByUsername(username));
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserAllFields> getByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByEmail(email));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NewUserResponse> addNewUser(@RequestBody NewUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(request));
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserAllFields> updateUser(@RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.save(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
