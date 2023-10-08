package com.bohdan.films_webapp.DAO;

import com.bohdan.films_webapp.DAO.enums.FilmStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "film_entity")
@Data
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

    @ManyToMany(mappedBy = "starredFilms", fetch = FetchType.LAZY)
    private Set<User> usersThatStarredTheFilm = new HashSet<>();

    @ManyToMany(mappedBy = "watchedFilms", fetch = FetchType.LAZY)
    private Set<User> usersThatWatchedTheFilm = new HashSet<>();

    @OneToMany(mappedBy = "film")
    private Set<Comment> comments = new HashSet<>();

    public Film() {
    }

    public Film(String name, int year, String imageName, String description) {
        this.name = name;
        this.year = year;
        this.imageName = imageName;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film film)) return false;
        return id == film.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", imageName='" + imageName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
