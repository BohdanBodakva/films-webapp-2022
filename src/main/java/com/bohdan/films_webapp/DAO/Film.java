package com.bohdan.films_webapp.DAO;

import com.bohdan.films_webapp.DAO.enums.FilmStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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

}
