package com.turing.javaproject.rest;

import com.turing.javaproject.entity.User;
import com.turing.javaproject.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users") //http://localhost:8080/api/v1/users/
@RequiredArgsConstructor
public class UserController {

    private final UserRepo userRepo;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}") //http://localhost:8080/api/v1/users/100500
    @ResponseBody
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return new ResponseEntity<>(userRepo.findById(id).get(), HttpStatusCode.valueOf(200));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        userRepo.save(user);
        return ResponseEntity.ok().build();
    }
}
