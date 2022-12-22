package com.bohdan.films_webapp.controllers;

import com.bohdan.films_webapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<String> adminPage(){
         return new ResponseEntity<>("Hello, Admin!", HttpStatus.OK);
    }
}
