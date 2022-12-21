package com.bohdan.films_webapp.repositories;

import com.bohdan.films_webapp.DAO.WatchedFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchedFilmRepository extends JpaRepository<WatchedFilm, Integer> {
    List<WatchedFilm> findWatchedFilmsByUserId(int userId);
    List<WatchedFilm> findWatchedFilmsByFilmId(int filmId);
}
