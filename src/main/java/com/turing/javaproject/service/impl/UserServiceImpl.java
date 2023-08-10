package com.turing.javaproject.service.impl;

import com.turing.javaproject.entity.RoleEnum;
import com.turing.javaproject.entity.User;
import com.turing.javaproject.exception.exceptions.common.WrongDateException;
import com.turing.javaproject.exception.exceptions.user.UserAlreadyExistsException;
import com.turing.javaproject.exception.exceptions.user.UserFieldsEmptyException;
import com.turing.javaproject.exception.exceptions.user.UserNotFoundException;
import com.turing.javaproject.repos.RoleRepo;
import com.turing.javaproject.repos.UserRepo;
import com.turing.javaproject.rest.dto.request.NewUserRequest;
import com.turing.javaproject.rest.dto.request.RegistrationRequest;
import com.turing.javaproject.rest.dto.request.UpdateUserRequest;
import com.turing.javaproject.rest.dto.response.NewUserResponse;
import com.turing.javaproject.rest.dto.response.UserAllFields;
import com.turing.javaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Override
    public UserAllFields save(UpdateUserRequest request) {
        if (request.getId() == null || !userRepo.existsById(request.getId())) {
            throw new UserNotFoundException(String.format("Error updating user with id = %d. " +
                    "User with such id does not exist in the database", request.getId()));
        }

        User user = User.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .avatarUrl(request.getAvatarUrl())
                .description(request.getDescription())
                .build();
        user.setId(request.getId());
        user.setPassword(userRepo.findPasswordByUserId(request.getId()));

        if (user.getBirthDate() != null && user.getBirthDate().isAfter(LocalDate.now())) {
            throw new WrongDateException("Дата рождения пользователя не может быть больше текущей");
        }

        checkUserRequiredFields(user);

        user = userRepo.save(user);

        return transform(user);
    }

    @Override
    public NewUserResponse add(NewUserRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .roles(Collections.singleton(roleRepo.findByName(RoleEnum.USER.getName()).get()))
                .build();

        checkUserRequiredFields(user);

        if (userRepo.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(String.format("User with username %s already exists!", request.getUsername()));
        }

        user = userRepo.save(user);

        return NewUserResponse.builder()
                .id(user.getId())
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .build();
    }

    @Override
    public void add(RegistrationRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .roles(Collections.singleton(roleRepo.findByName(RoleEnum.USER.getName()).get()))
                .build();

        checkUserRequiredFields(user);

        if (userRepo.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(String.format("User with username %s already exists!", request.getUsername()));
        }

        userRepo.save(user);
    }

    @Override
    public UserAllFields getById(Long id) {
        Optional<User> foundUser = userRepo.findById(id);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Could not find user with id %d", id));
        }

        return transform(foundUser.get());
    }

    @Override
    public UserAllFields getByUsername(String username) {
        Optional<User> foundUser = userRepo.findByUsername(username);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Could not find user with username %s", username));
        }

        return transform(foundUser.get());
    }

    @Override
    public UserAllFields getByEmail(String email) {
        Optional<User> foundUser = userRepo.findByEmail(email);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Could not find user with email %s", email));
        }

        return transform(foundUser.get());
    }

    @Override
    public List<UserAllFields> getAll() {
        return userRepo.findAll().stream()
                .map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new UserNotFoundException(String.format("Could not find user with id %d", id));
        }

        userRepo.deleteById(id);
    }

    private void checkUserRequiredFields(User user) throws UserFieldsEmptyException, WrongDateException {
        if (!StringUtils.hasText(user.getEmail())
                || !StringUtils.hasText(user.getUsername())
                || !StringUtils.hasText(user.getPassword())) {
            throw new UserFieldsEmptyException("У пользователя не заполнены обязательные поля");
        }
    }

    private UserAllFields transform(User user) {
        return UserAllFields.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .avatarUrl(user.getAvatarUrl())
                .description(user.getDescription())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElse(null);
    }
}
