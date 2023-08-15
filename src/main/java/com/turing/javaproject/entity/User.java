package com.turing.javaproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "usr", schema = "public")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends AbstractEntity implements UserDetails {
    @Column(name = "avatar", length = 50)
    private String avatarUrl;
    @Column(name = "description", length = 250)
    private String description;
    @Column(name = "username", length = 100, nullable = false, unique = true)
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usr_roles",
            joinColumns = @JoinColumn(name = "id_usr", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            roles = new HashSet<>();
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (!Objects.equals(avatarUrl, user.avatarUrl)) return false;
        if (!Objects.equals(description, user.description)) return false;
        if (!username.equals(user.username)) return false;
        if (!Objects.equals(fullName, user.fullName)) return false;
        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        return Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + username.hashCode();
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }
}
