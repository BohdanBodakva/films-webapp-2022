package com.bohdan.films_webapp.services;

import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DAO.WatchedFilm;
import com.bohdan.films_webapp.exception_handler.FilmNotFoundException;
import com.bohdan.films_webapp.exception_handler.WatchedFilmNotFoundException;

import java.util.List;

public interface WatchedFilmService {
    List<WatchedFilm> getAllWatchedFilms();
    WatchedFilm getWatchedFilmById(int id) throws WatchedFilmNotFoundException;
    void deleteWatchedFilmById(int id) throws WatchedFilmNotFoundException;
    List<WatchedFilm> getWatchedFilmsByUserId(int userId);
    List<WatchedFilm> getWatchedFilmsByFilmId(int filmId);
}
