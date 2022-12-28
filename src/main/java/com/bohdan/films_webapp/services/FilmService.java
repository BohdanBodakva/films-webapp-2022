package com.bohdan.films_webapp.services;

import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.exceptions.FilmNotFoundException;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;

import java.util.List;

public interface FilmService {
    List<Film> getAllFilms();
    List<Film> getAllWatchedFilmsByUserId(int userId) throws UserNotFoundException;
    List<Film> getAllStarredFilmsByUserId(int userId) throws UserNotFoundException;
    Film getWatchedFilmByUserIdAndFilmId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException;
    Film getStarredFilmByUserIdAndFilmId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException;
    void makeFilmWatchedByFilmIdAndUserId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException;
    void makeFilmStarredByFilmIdAndUserId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException;
    void makeFilmUnstarredByFilmIdAndUserId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException;
    List<Film> getAllDisplayedFilms();
    List<Film> getAllDeletedFilms();
    List<Film> getAllUnavailableFilms();
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
