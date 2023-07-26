package com.turing.javaproject.rest.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewUserRequest {
    private String username;
    private String password;
    private String email;
    private String fullName;
}
