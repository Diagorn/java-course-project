package com.turing.javaproject.rest.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserAllFields {
    private Long id;
    private String avatarUrl;
    private String description;
    private String username;
    private String fullName;
    private String email;
    private LocalDate birthDate;
}
