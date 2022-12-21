package com.bohdan.films_webapp.DAO;

import jakarta.persistence.*;

@Entity
@Table(name = "starred_film")
public class StarredFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
}
