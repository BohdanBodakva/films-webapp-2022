package com.bohdan.films_webapp.DTO;

import com.bohdan.films_webapp.DAO.Comment;
import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.DAO.enums.FilmStatus;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class FilmDto {
    private int id;
    private String name;
    private int year;
    private String imageName;
    private String description;
    private String status;
    private Set<Integer> usersThatStarredTheFilm;
    private Set<Integer> usersThatWatchedTheFilm;
    private Set<CommentDto> comments;

    public FilmDto(int id, String name,
                   int year,
                   String imageName,
                   String description,
                   String status,
                   Set<Integer> usersThatStarredTheFilm,
                   Set<Integer> usersThatWatchedTheFilm,
                   Set<CommentDto> comments) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.imageName = imageName;
        this.description = description;
        this.status = status;
        this.usersThatStarredTheFilm = usersThatStarredTheFilm;
        this.usersThatWatchedTheFilm = usersThatWatchedTheFilm;
        this.comments = comments;
    }

    public static FilmDto fromFilm(Film film){
        return new FilmDto(
                film.getId(),
                film.getName(),
                film.getYear(),
                film.getImageName(),
                film.getDescription(),
                film.getStatus().name(),
                film.getUsersThatStarredTheFilm()
                        .stream().map(User::getId).collect(Collectors.toSet()),
                film.getUsersThatWatchedTheFilm()
                        .stream().map(User::getId).collect(Collectors.toSet()),
                CommentDto.fromCommentSet(film.getComments())
        );
    }

    public static Set<FilmDto> fromFilmList(List<Film> films){
        return films.stream()
                .map(FilmDto::fromFilm)
                .collect(Collectors.toSet());
    }
}
