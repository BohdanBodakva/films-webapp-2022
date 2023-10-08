package com.bohdan.films_webapp.services;

import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.exceptions.FilmNotFoundException;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface FilmService {
    Set<Film> getAllFilms();
    Set<Film> getAllWatchedFilmsByUserId(int userId) throws UserNotFoundException;
    Set<Film> getAllStarredFilmsByUserId(int userId) throws UserNotFoundException;
    Film getWatchedFilmByUserIdAndFilmId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException;
    Film getStarredFilmByUserIdAndFilmId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException;
    void makeFilmWatchedByFilmIdAndUserId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException;
    void makeFilmStarredByFilmIdAndUserId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException;
    void makeFilmUnstarredByFilmIdAndUserId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException;
    Set<Film> getAllDisplayedFilms();
    Set<Film> getAllDeletedFilms();
    Set<Film> getAllUnavailableFilms();
    Film getFilmById(int id) throws FilmNotFoundException;
    Film getFilmByIdIfDisplayed(int id) throws FilmNotFoundException;
    Film saveFilm(Film film) ;
    Film updateFilmById(int id, Film film) throws FilmNotFoundException;
    void makeFilmDeletedById(int id) throws FilmNotFoundException;
    void deleteFilmById(int id) throws FilmNotFoundException;
    void makeFilmUnavailableById(int id) throws FilmNotFoundException;
    void makeFilmDisplayedById(int id) throws FilmNotFoundException;
    void deleteAllWatchedFilmsByUserId(int userId) throws UserNotFoundException;
    void deleteAllStarredFilmsByUserId(int userId) throws UserNotFoundException;
    void deleteStarredFilmByFilmIdAndUserId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException;
}
