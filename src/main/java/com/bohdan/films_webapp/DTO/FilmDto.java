package com.bohdan.films_webapp.DTO;

import com.bohdan.films_webapp.DAO.Comment;
import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DAO.User;
import lombok.Data;

import java.util.Comparator;
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
    private List<List<String>> comments;

    public FilmDto(int id, String name,
                   int year,
                   String imageName,
                   String description,
                   String status,
                   Set<Integer> usersThatStarredTheFilm,
                   Set<Integer> usersThatWatchedTheFilm,
                   List<List<String>> comments) {
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
                film.getComments()
                        .stream()
                        .sorted(Comparator.comparing(Comment::getDate).reversed())
                        .map(comment -> List.of(
                                comment.getComment(),
                                comment.getUser().getEmail(),
                                comment.getDate().toString()
                        ))
                        .collect(Collectors.toList())
        );
    }

    public static Set<FilmDto> fromFilmSet(Set<Film> films){
        return films.stream()
                .map(FilmDto::fromFilm)
                .collect(Collectors.toSet());
    }
}
