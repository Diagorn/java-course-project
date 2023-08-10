package com.turing.javaproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "public", name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usr_roles",
        inverseJoinColumns = @JoinColumn(name = "id_usr", referencedColumnName = "id"),
        joinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private List<User> users;
}
