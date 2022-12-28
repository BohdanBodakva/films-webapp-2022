package com.bohdan.films_webapp.DAO;

import com.bohdan.films_webapp.DAO.enums.Role;
import com.bohdan.films_webapp.DAO.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @ManyToMany
    @JoinTable(
            name = "starred_film",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private Set<Film> starredFilms = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "watched_film",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private Set<Film> watchedFilms = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && role == user.role && Objects.equals(password, user.password) && status == user.status && Objects.equals(starredFilms, user.starredFilms) && Objects.equals(watchedFilms, user.watchedFilms) && Objects.equals(comments, user.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, role, password, status, starredFilms, watchedFilms, comments);
    }
}
