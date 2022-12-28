package com.bohdan.films_webapp.controllers;

import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DTO.FilmDto;
import com.bohdan.films_webapp.DTO.UserDto;
import com.bohdan.films_webapp.exceptions.*;
import com.bohdan.films_webapp.services.CommentService;
import com.bohdan.films_webapp.services.FilmService;
import com.bohdan.films_webapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final FilmService filmService;
    private final CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<String> adminPage(){
         return new ResponseEntity<>("Hello, Admin!", HttpStatus.OK);
    }

//  ========================================== USERS ========================================================

    @GetMapping("/users")
    public ResponseEntity<Set<UserDto>> getAllUsers(){
        return new ResponseEntity<>(UserDto.fromUserList(userService.getAllUsers()), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") int userId){
        try {
            return new ResponseEntity<>(UserDto.fromUser(userService.getUserById(userId)), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/banned")
    public ResponseEntity<Set<UserDto>> getAllBannedUsers(){
        return new ResponseEntity<>(UserDto.fromUserList(userService.getAllBannedUsers()), HttpStatus.OK);
    }

    @GetMapping("/users/active")
    public ResponseEntity<Set<UserDto>> getAllActiveUsers(){
        return new ResponseEntity<>(UserDto.fromUserList(userService.getAllActiveUsers()), HttpStatus.OK);
    }

    @GetMapping("/users/deleted")
    public ResponseEntity<Set<UserDto>> getAllDeletedUsers(){
        return new ResponseEntity<>(UserDto.fromUserList(userService.getAllDeletedUsers()), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/delete")
    public ResponseEntity<?> makeUserDeletedById(@PathVariable("userId") int userId){
        try {
            userService.makeUserDeletedById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/{userId}/ban")
    public ResponseEntity<?> banUserById(@PathVariable("userId") int userId){
        try {
            userService.makeUserBannedById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/{userId}/makeActive")
    public ResponseEntity<?> makeUserActiveById(@PathVariable("userId") int userId){
        try {
            userService.makeUserActiveById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> removeUserById(@PathVariable("userId") int userId){
        try {
            userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//  ========================================== USER FILMS ========================================================

    @GetMapping("/users/{userId}/watched")
    public ResponseEntity<?> getWatchedFilmsByUserId(@PathVariable("userId") int userId){
        try {
            return new ResponseEntity<>(
                    FilmDto.fromFilmList(filmService.getAllWatchedFilmsByUserId(userId)), HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{userId}/starred")
    public ResponseEntity<?> getStarredFilmsByUserId(@PathVariable("userId") int userId){
        try {
            return new ResponseEntity<>(
                    FilmDto.fromFilmList(filmService.getAllStarredFilmsByUserId(userId)), HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//  ========================================== FILMS ========================================================

    @GetMapping("/films")
    public ResponseEntity<Set<FilmDto>> getAllFilms(){
        return new ResponseEntity<>(FilmDto.fromFilmList(filmService.getAllFilms()), HttpStatus.OK);
    }

    @GetMapping("/films/displayed")
    public ResponseEntity<Set<FilmDto>> getAllDisplayedFilms(){
        return new ResponseEntity<>(FilmDto.fromFilmList(filmService.getAllDisplayedFilms()), HttpStatus.OK);
    }

    @GetMapping("/films/deleted")
    public ResponseEntity<Set<FilmDto>> getAllDeletedFilms(){
        return new ResponseEntity<>(FilmDto.fromFilmList(filmService.getAllDeletedFilms()), HttpStatus.OK);
    }

    @GetMapping("/films/unavailable")
    public ResponseEntity<Set<FilmDto>> getAllUnavailableFilms(){
        return new ResponseEntity<>(FilmDto.fromFilmList(filmService.getAllUnavailableFilms()), HttpStatus.OK);
    }

    @GetMapping("/films/{filmId}")
    public ResponseEntity<?> getFilmById(@PathVariable("filmId") int filmId){
        try {
            return new ResponseEntity<>(
                    FilmDto.fromFilm(filmService.getFilmById(filmId)), HttpStatus.OK
            );
        } catch (FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/films")
    public ResponseEntity<?> saveFilm(@RequestBody Film film){
        return new ResponseEntity<>(
                FilmDto.fromFilm(filmService.saveFilm(film)), HttpStatus.OK
        );
    }

    @PutMapping("/films/{filmId}")
    public ResponseEntity<?> updateFilmById(@PathVariable("filmId") int filmId,
                                            @RequestBody Film film){
        try {
            return new ResponseEntity<>(
                    FilmDto.fromFilm(filmService.updateFilmById(filmId, film)), HttpStatus.OK
            );
        } catch (FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/films/{filmId}/delete")
    public ResponseEntity<?> makeFilmDeletedById(@PathVariable("filmId") int filmId){
        try {
            filmService.makeFilmDeletedById(filmId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/films/{filmId}/makeDisplayed")
    public ResponseEntity<?> makeFilmDisplayedById(@PathVariable("filmId") int filmId){
        try {
            filmService.makeFilmDisplayedById(filmId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/films/{filmId}/makeUnavailable")
    public ResponseEntity<?> makeFilmUnavailableById(@PathVariable("filmId") int filmId){
        try {
            filmService.makeFilmUnavailableById(filmId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/films/{filmId}")
    public ResponseEntity<?> removeFilmById(@PathVariable("filmId") int filmId){
        try {
            filmService.deleteFilmById(filmId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//  ========================================== COMMENTS ========================================================

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable("commentId") int commentId){
        try {
            commentService.deleteCommentById(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CommentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
