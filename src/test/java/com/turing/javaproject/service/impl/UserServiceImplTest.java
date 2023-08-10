package com.turing.javaproject.service.impl;

import com.turing.javaproject.entity.Role;
import com.turing.javaproject.entity.RoleEnum;
import com.turing.javaproject.entity.User;
import com.turing.javaproject.exception.exceptions.user.UserFieldsEmptyException;
import com.turing.javaproject.repos.RoleRepo;
import com.turing.javaproject.repos.UserRepo;
import com.turing.javaproject.rest.dto.request.NewUserRequest;
import com.turing.javaproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

class UserServiceImplTest {

    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepo roleRepo;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserServiceImpl(userRepo, passwordEncoder, roleRepo);
    }

    @Test
    void saveSuccess() {

    }

    @Test
    @WithMockUser(value = "Admin")
    @Sql(value = {"/sql/before/insert-default-admin.sql", "/sql/before/add-roles.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void itShouldSuccessfullySaveNewUser() {
        // Given
        Role userRole = new Role("USER", new ArrayList<>());

        NewUserRequest request = NewUserRequest.builder()
                .username("Diagorn")
                .password("qwerty123")
                .email("some@mail.ru")
                .fullName("Гасин Михаил Александрович")
                .build();

        // ...an expected user
        User user = User.builder()
                .username("Diagorn")
                .password("qwerty123")
                .email("some@mail.ru")
                .fullName("Гасин Михаил Александрович")
                .roles(Collections.singleton(userRole))
                .build();

        User userFromDB = User.builder()
                .username("Diagorn")
                .password("qwerty123")
                .email("some@mail.ru")
                .fullName("Гасин Михаил Александрович")
                .roles(Collections.singleton(userRole))
                .build();
        userFromDB.setId(1L);

        // ...return an updated user when querying database
        given(userRepo.save(any(User.class)))
                .willReturn(userFromDB);

        given(roleRepo.findByName(RoleEnum.USER.getName()))
                .willReturn(Optional.of(userRole));

        given(passwordEncoder.encode(any()))
                .willReturn("qwerty123");

        // When
        userService.add(request);

        // Then
        then(userRepo).should().save(userArgumentCaptor.capture());
        User captoredUser = userArgumentCaptor.getValue();
        assertThat(captoredUser).isEqualTo(user);
    }

    @Test()
    @WithMockUser(value = "Admin")
    @Sql(value = {"/sql/before/insert-default-admin.sql", "/sql/before/add-roles.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void itShouldNeverSaveNewUserWhenEmailIsNull() {

        // Given a request without email
        NewUserRequest request = NewUserRequest.builder()
                .username("Diagorn")
                .password("qwerty123")
                .fullName("Гасин Михаил Александрович")
                .build();

        // When
        given(roleRepo.findByName(RoleEnum.USER.getName()))
                .willReturn(Optional.of(new Role("USER", new ArrayList<>())));

        // Then
        assertThatThrownBy(() -> userService.add(request))
                .isInstanceOf(UserFieldsEmptyException.class)
                .hasMessage("У пользователя не заполнены обязательные поля");

        then(userRepo).should(never()).save(any(User.class));
    }

    @Test
    void getById() {
    }

    @Test
    void getByUsername() {
    }

    @Test
    void getByEmail() {
    }

    @Test
    void getAll() {
    }

    @Test
    void delete() {
    }
}