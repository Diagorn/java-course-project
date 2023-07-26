package com.turing.javaproject.rest.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewUserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
}
