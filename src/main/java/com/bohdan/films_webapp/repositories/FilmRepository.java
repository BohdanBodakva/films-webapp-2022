package com.bohdan.films_webapp.repositories;

import com.bohdan.films_webapp.DAO.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
    @Query("select f from Film f where f.status='DELETED'")
    List<Film> findAllDeletedFilms();

    @Query("select f from Film f where f.status='DISPLAYED'")
    List<Film> findAllDisplayedFilms();

    @Query("select f from Film f where f.status='TEMPORARILY_UNAVAILABLE'")
    List<Film> findAllUnavailableFilms();

    @Query("select f from Film f inner join f.usersThatWatchedTheFilm uw inner join uw.watchedFilms where uw.id=:userId")
    List<Film> findAllWatchedFilmsByUserId(int userId);

    @Query("select f from Film f inner join f.usersThatStarredTheFilm uw inner join uw.starredFilms where uw.id=:userId")
    List<Film> findAllStarredFilmsByUserId(int userId);

    @Query("select f from Film f inner join f.usersThatWatchedTheFilm uw inner join uw.watchedFilms where uw.id=:userId and f.id=:filmId")
    Optional<Film> findWatchedFilmByUserIdAndFilmId(int userId, int filmId);

    @Query("select f from Film f inner join f.usersThatStarredTheFilm uw inner join uw.starredFilms where uw.id=:userId and f.id=:filmId")
    Optional<Film> findStarredFilmByUserIdAndFilmId(int userId, int filmId);

}
