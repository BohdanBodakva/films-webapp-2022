package com.bohdan.films_webapp.exceptions;

public class WatchedFilmNotFoundException extends Exception{
    public WatchedFilmNotFoundException(String message) {
        super(message);
    }
}
