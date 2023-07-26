package com.turing.javaproject.service.impl;

import com.turing.javaproject.entity.User;
import com.turing.javaproject.exception.exceptions.common.WrongDateException;
import com.turing.javaproject.exception.exceptions.user.UserAlreadyExistsException;
import com.turing.javaproject.exception.exceptions.user.UserFieldsEmptyException;
import com.turing.javaproject.exception.exceptions.user.UserNotFoundException;
import com.turing.javaproject.repos.UserRepo;
import com.turing.javaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public User save(User user) {
        if (user.getId() == null || !userRepo.existsById(user.getId())) {
            throw new UserNotFoundException(String.format("Error updating user with id = %d. " +
                    "User with such id does not exist in the database", user.getId()));
        }

        checkUser(user);

        userRepo.save(user);
        return user;
    }

    @Override
    public User add(User user) {
        if (user.getId() != null && userRepo.existsById(user.getId())) {
            throw new UserAlreadyExistsException(String.format("User with id = %d already exists", user.getId()));
        }

        checkUser(user);

        user = userRepo.save(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        Optional<User> foundUser = userRepo.findById(id);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Could not find user with id %d", id));
        }

        return foundUser.get();
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> foundUser = userRepo.findByUsername(username);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Could not find user with username %s", username));
        }

        return foundUser.get();
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> foundUser = userRepo.findByEmail(email);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Could not find user with username %s", email));
        }

        return foundUser.get();
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    private void checkUser(User user) throws UserFieldsEmptyException, WrongDateException {
        if (!StringUtils.hasText(user.getEmail())
                || !StringUtils.hasText(user.getUsername())
                || !StringUtils.hasText(user.getPassword())) {
            throw new UserFieldsEmptyException("У пользователя не заполнены обязательные поля");
        }

        if (user.getBirthDate() != null && user.getBirthDate().isAfter(LocalDate.now())) {
            throw new WrongDateException("Дата рождения пользователя не может быть больше текущей");
        }
    }
}
