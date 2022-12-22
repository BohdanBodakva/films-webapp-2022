package com.bohdan.films_webapp.validation;

import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DAO.User;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.regex.Pattern;

@Component
public class CustomValidation {
    private final String nameValidation = "(^[A-Z]{1})([a-z]+){1,29}$";
    private final String emailValidation = "(^(.+)@(\\S+)){5,70}$";
    private final String filmNameValidation = "(^[A-Z])([a-z]+){1,50}$";
    private final Pattern namePattern;
    private final Pattern emailPattern;
    private final Pattern filmNamePattern;

    public CustomValidation() {
        namePattern = Pattern.compile(nameValidation);
        emailPattern = Pattern.compile(emailValidation);
        filmNamePattern = Pattern.compile(filmNameValidation);
    }

    public boolean validateUser(User user){
        return Pattern.matches(nameValidation, user.getName()) &&
                Pattern.matches(nameValidation, user.getSurname()) &&
                Pattern.matches(emailValidation, user.getEmail()) &&
                user.getRole() != null &&
                user.getPassword() != null && user.getPassword().length() > 5 && user.getPassword().length() < 50 &&
                user.getStatus() != null;
    }

    public boolean validateUserForUpdating(User user){
        boolean validated = true;

        if(user.getName() != null){
            validated = Pattern.matches(nameValidation, user.getName());
        }
        if(user.getSurname() != null){
            validated =  Pattern.matches(nameValidation, user.getSurname());
        }
        if(user.getEmail() != null){
            validated = Pattern.matches(emailValidation, user.getEmail());
        }
        if(user.getPassword() != null){
            validated = user.getPassword().length() > 5 && user.getPassword().length() < 50;
        }

        return validated;
    }

    public boolean validateFilm(Film film){
        return Pattern.matches(filmNameValidation, film.getName()) &&
                                film.getYear() > 1700 && film.getYear() <= Year.now().getValue() &&
                                film.getImageName().length() > 0 &&
                                film.getStatus() != null;
    }

    public boolean validateFilmForUpdating(Film film){
        boolean validated = true;

        if(film.getName() != null){
            validated = Pattern.matches(filmNameValidation, film.getName());
        }
        if(film.getYear() != 0){
            validated = film.getYear() > 1700 && film.getYear() <= Year.now().getValue();
        }
        if(film.getImageName() != null){
            validated = film.getImageName().length() > 0;
        }

        return validated;
    }
}
