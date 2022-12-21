package com.bohdan.films_webapp.exception_handler;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
