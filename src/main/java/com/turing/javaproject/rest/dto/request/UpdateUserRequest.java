package com.turing.javaproject.rest.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UpdateUserRequest {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String description;
    private LocalDate birthDate;
}
