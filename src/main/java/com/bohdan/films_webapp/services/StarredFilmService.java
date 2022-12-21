package com.bohdan.films_webapp.services;

import com.bohdan.films_webapp.DAO.StarredFilm;
import com.bohdan.films_webapp.DAO.WatchedFilm;
import com.bohdan.films_webapp.exception_handler.StarredFilmNotFoundException;
import com.bohdan.films_webapp.exception_handler.WatchedFilmNotFoundException;

import java.util.List;

public interface StarredFilmService {
    List<StarredFilm> getAllStarredFilms();
    StarredFilm getStarredFilmById(int id) throws StarredFilmNotFoundException;
    void deleteStarredFilmById(int id) throws StarredFilmNotFoundException;
    List<StarredFilm> getStarredFilmsByUserId(int userId);
    List<StarredFilm> getStarredFilmsByFilmId(int filmId);
}
