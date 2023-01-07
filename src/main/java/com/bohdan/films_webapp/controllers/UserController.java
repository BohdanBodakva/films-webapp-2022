package com.bohdan.films_webapp.controllers;

import com.bohdan.films_webapp.DAO.Comment;
import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.DTO.CommentDto;
import com.bohdan.films_webapp.DTO.FilmDto;
import com.bohdan.films_webapp.DTO.UserDto;
import com.bohdan.films_webapp.exceptions.CommentNotFoundException;
import com.bohdan.films_webapp.exceptions.EmailAlreadyExistsException;
import com.bohdan.films_webapp.exceptions.FilmNotFoundException;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;
import com.bohdan.films_webapp.services.CommentService;
import com.bohdan.films_webapp.services.FilmService;
import com.bohdan.films_webapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FilmService filmService;
    private final CommentService commentService;

//  ========================================== USERS ========================================================

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserByIdIfActive(@PathVariable("userId") int userId){
        try {
            return new ResponseEntity<>(
                    UserDto.fromUser(userService.getUserByIdIfActive(userId)), HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        return new ResponseEntity<>(
                UserDto.fromUser(userService.saveUser(user)), HttpStatus.OK
        );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserById(@PathVariable("userId") int userId,
                                               @RequestBody User user){
        try {
            return new ResponseEntity<>(
                    UserDto.fromUser(userService.updateUserById(userId, user)), HttpStatus.OK
            );
        } catch (UserNotFoundException | EmailAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<?> makeUserDeletedById(@PathVariable("userId") int userId){
        try {
            userService.makeUserDeletedById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//  ========================================== USER FILMS ========================================================

    @GetMapping("/{userId}/films")
    public ResponseEntity<Set<FilmDto>> getAllDisplayedFilms(@PathVariable("userId") int userId){
        return new ResponseEntity<>(FilmDto.fromFilmSet(filmService.getAllDisplayedFilms()), HttpStatus.OK);
    }

    @GetMapping("/{userId}/films/{filmId}")
    public ResponseEntity<?> getDisplayedFilmById(@PathVariable("userId") int userId,
                                                              @PathVariable("filmId") int filmId){
        try {
            filmService.makeFilmWatchedByFilmIdAndUserId(userId, filmId);
            return new ResponseEntity<>(
                    FilmDto.fromFilm(filmService.getFilmByIdIfDisplayed(filmId)), HttpStatus.OK
            );
        } catch (FilmNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{userId}/films/{filmId}/makeStarred")
    public ResponseEntity<?> makeFilmStarredByFilmIdAndUserIdAndSave(@PathVariable("userId") int userId,
                                                                     @PathVariable("filmId") int filmId){
        try {
            filmService.makeFilmStarredByFilmIdAndUserId(userId, filmId);
            return new ResponseEntity<>(
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{userId}/films/{filmId}/makeUnstarred")
    public ResponseEntity<?> makeFilmUnstarredByFilmIdAndUserIdAndSave(@PathVariable("userId") int userId,
                                                                       @PathVariable("filmId") int filmId){
        try {
            filmService.makeFilmUnstarredByFilmIdAndUserId(userId, filmId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException | FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}/watched")
    public ResponseEntity<?> getAllWatchedFilmsByUserId(@PathVariable("userId") int userId){
        try {
            return new ResponseEntity<>(
                   FilmDto.fromFilmSet(filmService.getAllWatchedFilmsByUserId(userId)), HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}/watched")
    public ResponseEntity<?> deleteAllWatchedFilmsByUserId(@PathVariable("userId") int userId){
        try {
            filmService.deleteAllWatchedFilmsByUserId(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}/starred")
    public ResponseEntity<?> getAllStarredFilmsByUserId(@PathVariable("userId") int userId){
        try {
            return new ResponseEntity<>(
                    FilmDto.fromFilmSet(filmService.getAllStarredFilmsByUserId(userId)), HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}/starred")
    public ResponseEntity<?> deleteAllStarredFilmsByUserId(@PathVariable("userId") int userId){
        try {
            filmService.deleteAllStarredFilmsByUserId(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}/starred/{filmId}")
    public ResponseEntity<?> deleteStarredFilmByFilmIdUserId(@PathVariable("userId") int userId,
                                                             @PathVariable("filmId") int filmId){
        try {
            filmService.deleteStarredFilmByFilmIdAndUserId(userId, filmId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException | FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }





    //  ========================================== COMMENTS ========================================================

    @GetMapping("/{userId}/films/{watchedFilmId}/comments")
    public ResponseEntity<?> getAllCommentsToFilm(@PathVariable("userId") int userId,
                                                  @PathVariable("watchedFilmId") int watchedFilmId){
        try {
            return new ResponseEntity<>(
                    CommentDto.fromCommentSet(commentService.getAllCommentsToFilmSortedByDateAsc(watchedFilmId)), HttpStatus.OK
            );
        } catch (FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/{userId}/films/{watchedFilmId}/comments")
    public ResponseEntity<?> addCommentToFilm(@PathVariable("userId") int userId,
                                                  @PathVariable("watchedFilmId") int watchedFilmId,
                                                  @RequestBody Comment comment){
        try {
            commentService.saveComment(userId, watchedFilmId, comment);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException | FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}/films/{watchedFilmId}/comments/{commentId}")
    public ResponseEntity<?> deleteCommentToFilm(@PathVariable("userId") int userId,
                                                  @PathVariable("watchedFilmId") int watchedFilmId,
                                                  @PathVariable("commentId") int commentId){
        try {
            commentService.makeCommentDeletedByCommentIdAndUserIdAndFilmId(commentId, userId, watchedFilmId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CommentNotFoundException | UserNotFoundException | FilmNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
