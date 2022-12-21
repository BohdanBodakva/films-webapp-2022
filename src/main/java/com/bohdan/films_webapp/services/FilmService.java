package com.bohdan.films_webapp.services;

import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.exception_handler.FilmNotFoundException;

import java.util.List;

public interface FilmService {
    List<Film> getAllFilms();
    List<Film> getAllAvailableFilms();
    Film getFilmById(int id) throws FilmNotFoundException;
    Film getFilmByIdIfAvailable(int id) throws FilmNotFoundException;
    Film saveFilm(Film film);
    Film updateFilmById(int id, Film film) throws FilmNotFoundException;
    void deleteFilmById(int id) throws FilmNotFoundException;
    void makeFilmUnavailableById(int id) throws FilmNotFoundException;
    void makeFilmDisplayedById(int id) throws FilmNotFoundException;
}
