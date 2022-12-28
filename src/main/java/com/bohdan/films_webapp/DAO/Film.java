package com.bohdan.films_webapp.DAO;

import com.bohdan.films_webapp.DAO.enums.FilmStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "film")
@Data
@RequiredArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private int year;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FilmStatus status;

    @ManyToMany(mappedBy = "starredFilms")
    private Set<User> usersThatStarredTheFilm = new HashSet<>();

    @ManyToMany(mappedBy = "watchedFilms")
    private Set<User> usersThatWatchedTheFilm = new HashSet<>();

    @OneToMany(mappedBy = "film")
    private Set<Comment> comments = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film film)) return false;
        return id == film.id && year == film.year && Objects.equals(name, film.name) && Objects.equals(imageName, film.imageName) && Objects.equals(description, film.description) && status == film.status && Objects.equals(usersThatStarredTheFilm, film.usersThatStarredTheFilm) && Objects.equals(usersThatWatchedTheFilm, film.usersThatWatchedTheFilm) && Objects.equals(comments, film.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, imageName, description, status, usersThatStarredTheFilm, usersThatWatchedTheFilm, comments);
    }
}
