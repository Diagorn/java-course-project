package com.turing.javaproject.rest.controller;

import com.turing.javaproject.rest.dto.request.RegistrationRequest;
import com.turing.javaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        userService.add(request);
        return ResponseEntity.ok().build();
    }
}
