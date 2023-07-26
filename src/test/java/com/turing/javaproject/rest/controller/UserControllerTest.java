package com.turing.javaproject.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turing.javaproject.repos.UserRepo;
import com.turing.javaproject.rest.dto.request.NewUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(value = "/sql/clear-usr-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void itShouldAddNewUser() throws Exception {
        // Given a new user request
        String username = "Diagorn";
        NewUserRequest request = NewUserRequest.builder()
                .username(username)
                .password("qwerty123")
                .email("some@mail.ru")
                .fullName("Гасин Михаил Александрович")
                .build();

        // When
        ResultActions postNewUserActions = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(javaToJson(request))
        );

        // Then
        postNewUserActions.andExpect(status().is2xxSuccessful());
        assertThat(userRepo.findByUsername(username))
                .isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u.getEmail()).isEqualTo(request.getEmail());
                    assertThat(u.getUsername()).isEqualTo(request.getUsername());
                    assertThat(u.getFullName()).isEqualTo(request.getFullName());
                    assertThat(u.getPassword()).isEqualTo(request.getPassword());
                });
    }

    private String javaToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed java to json");
            return null;
        }
    }
}