package com.bohdan.films_webapp.exception_handler;

public class FilmNotFoundException extends Exception{
    public FilmNotFoundException(String message) {
        super(message);
    }
}
