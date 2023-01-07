package com.bohdan.films_webapp.controllers;

import com.bohdan.films_webapp.DTO.CommentDto;
import com.bohdan.films_webapp.DTO.FilmDto;
import com.bohdan.films_webapp.exceptions.FilmNotFoundException;
import com.bohdan.films_webapp.services.CommentService;
import com.bohdan.films_webapp.services.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<Set<FilmDto>> getAllDisplayedFilms(){
        return new ResponseEntity<>(FilmDto.fromFilmSet(filmService.getAllDisplayedFilms()), HttpStatus.OK);
    }

    @GetMapping("/{filmId}")
    public ResponseEntity<?> getDisplayedFilmById(@PathVariable("filmId") int filmId){
        try {
            return new ResponseEntity<>(
                    FilmDto.fromFilm(filmService.getFilmByIdIfDisplayed(filmId)), HttpStatus.OK
            );
        } catch (FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{watchedFilmId}/comments")
    public ResponseEntity<?> getAllCommentsToFilm(@PathVariable("watchedFilmId") int watchedFilmId){
        try {
            return new ResponseEntity<>(
                    CommentDto.fromCommentSet(commentService.getAllCommentsToFilmSortedByDateAsc(watchedFilmId)), HttpStatus.OK
            );
        } catch (FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
