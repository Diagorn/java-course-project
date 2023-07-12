package com.turing.javaproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "usr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity{
    @Column(name = "avatar", length = 50)
    private String avatarUrl;
    @Column(name = "description", length = 250)
    private String description;
    @Column(name = "username", length = 100, nullable = false)
    private String username;
    @Column(name = "full_name", length = 250)
    private String fullName;
    @Column(name = "email", length = 250, nullable = false)
    private String email;
    @Column(name = "password", length = 200, nullable = false)
    private String password;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "author", fetch = FetchType.LAZY)
    private List<Article> articles;
    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "author", fetch = FetchType.LAZY)
    private List<Comment> comments;
}