package com.bohdan.films_webapp.repositories;

import com.bohdan.films_webapp.DAO.StarredFilm;
import com.bohdan.films_webapp.DAO.WatchedFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StarredFilmRepository extends JpaRepository<StarredFilm, Integer> {
    List<StarredFilm> findStarredFilmsByUserId(int userId);
    List<StarredFilm> findStarredFilmsByFilmId(int filmId);
}
