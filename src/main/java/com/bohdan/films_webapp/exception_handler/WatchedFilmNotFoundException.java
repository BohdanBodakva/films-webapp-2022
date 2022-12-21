package com.bohdan.films_webapp.exception_handler;

public class WatchedFilmNotFoundException extends Exception{
    public WatchedFilmNotFoundException(String message) {
        super(message);
    }
}
