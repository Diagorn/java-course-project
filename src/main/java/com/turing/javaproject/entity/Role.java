package com.turing.javaproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usr_roles",
        inverseJoinColumns = @JoinColumn(name = "id_usr", referencedColumnName = "id"),
        joinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private List<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Role role = (Role) o;

        if (!Objects.equals(getId(), role.getId())) return false;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + name.hashCode();
        return result;
    }
}
