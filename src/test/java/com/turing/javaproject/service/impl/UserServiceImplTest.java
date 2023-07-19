package com.turing.javaproject.service.impl;

import com.turing.javaproject.entity.User;
import com.turing.javaproject.exception.exceptions.common.WrongDateException;
import com.turing.javaproject.exception.exceptions.user.UserFieldsEmptyException;
import com.turing.javaproject.repos.UserRepo;
import com.turing.javaproject.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @Test
    void saveSuccess() {

    }

    @Test
    void addSuccess() {

        User user = new User();
        user.setEmail("some@mail.ru");
        user.setUsername("Diagorn");
        user.setPassword("qwerty123");
        user.setBirthDate(LocalDate.now().minus(23, ChronoUnit.YEARS));

        User userFromDb = new User();
        userFromDb.setEmail("some@mail.ru");
        userFromDb.setUsername("Diagorn");
        userFromDb.setPassword("qwerty123");
        userFromDb.setBirthDate(LocalDate.now().minus(23, ChronoUnit.YEARS));
        userFromDb.setId(1L);

        doReturn(userFromDb)
                .when(userRepo)
                .save(user);

        User savedUser = userService.add(user);

        Assertions.assertEquals(savedUser.getId(), 1L);
        Assertions.assertEquals(savedUser.getUsername(), "Diagorn");
        Assertions.assertEquals(savedUser.getEmail(), "some@mail.ru");
        Assertions.assertEquals(savedUser.getPassword(), "qwerty123");

        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test()
    void addErrorFieldsEmpty() {
        User user = new User();
        user.setEmail("some@mail.ru");
        user.setPassword("qwerty123");
        user.setBirthDate(LocalDate.now().minus(23, ChronoUnit.YEARS));

        assertThatExceptionOfType(UserFieldsEmptyException.class)
                .isThrownBy(() -> userService.add(user))
                .withMessage("У пользователя не заполнены обязательные поля");

        user.setUsername("Diagorn");
        user.setEmail(null);

        assertThatExceptionOfType(UserFieldsEmptyException.class)
                .isThrownBy(() -> userService.add(user))
                .withMessage("У пользователя не заполнены обязательные поля");

        user.setEmail("some@mpei.ru");
        user.setPassword(null);

        assertThatExceptionOfType(UserFieldsEmptyException.class)
                .isThrownBy(() -> userService.add(user))
                .withMessage("У пользователя не заполнены обязательные поля");

        user.setPassword("password");
        user.setBirthDate(LocalDate.now().plus(1, ChronoUnit.DAYS));

        assertThatExceptionOfType(WrongDateException.class)
                .isThrownBy(() -> userService.add(user))
                .withMessage("Дата рождения пользователя не может быть больше текущей");
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