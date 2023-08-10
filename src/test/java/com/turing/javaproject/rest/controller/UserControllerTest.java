package com.turing.javaproject.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turing.javaproject.repos.UserRepo;
import com.turing.javaproject.rest.dto.request.NewUserRequest;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(value = "Admin", authorities = {"USER", "ADMIN"})
    @Sql(value = {"/sql/after/clear-usr-table.sql", "/sql/before/add-roles.sql",
            "/sql/before/insert-default-admin.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
                    assertThat(u.getPassword()).isNotBlank();
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