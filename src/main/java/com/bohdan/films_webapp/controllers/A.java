package com.bohdan.films_webapp.controllers;

import com.bohdan.films_webapp.services.CommentService;
import com.bohdan.films_webapp.services.FilmService;
import com.bohdan.films_webapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/a")
@RequiredArgsConstructor
public class A {
    private final UserService userService;
    private final FilmService filmService;
    private final CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<String> adminPage() {
        return new ResponseEntity<>("Hello!", HttpStatus.OK);
    }
}
