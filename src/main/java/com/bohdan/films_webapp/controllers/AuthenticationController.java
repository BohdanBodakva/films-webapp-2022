package com.bohdan.films_webapp.controllers;

import com.bohdan.films_webapp.authentication.AuthenticationRequest;
import com.bohdan.films_webapp.authentication.AuthenticationResponse;
import com.bohdan.films_webapp.authentication.AuthenticationService;
import com.bohdan.films_webapp.authentication.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ){
        return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ){
        return new ResponseEntity<>(authenticationService.authenticate(authenticationRequest), HttpStatus.OK);
    }
}
