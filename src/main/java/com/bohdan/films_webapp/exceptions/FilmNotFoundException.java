package com.bohdan.films_webapp.exceptions;

public class FilmNotFoundException extends Exception{
    public FilmNotFoundException(String message) {
        super(message);
    }
}
