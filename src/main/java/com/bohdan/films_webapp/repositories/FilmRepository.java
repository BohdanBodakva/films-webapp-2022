package com.bohdan.films_webapp.repositories;

import com.bohdan.films_webapp.DAO.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

//    List<Film> findAllByStatusDeleted();
//    List<Film> findAllByStatusDisplayed();
//    List<Film> findAllByStatusTemporarilyUnavailable();
//
//    Set<Film> findByUsers_UserId(int userId);
//    Set<Film> findByUsers_UserId(int userId);
//
//    Optional<Film> findFilmByUser




    @Query("select f from Film f where f.status='DELETED'")
    List<Film> findAllDeletedFilms();

    @Query("select f from Film f where f.status='DISPLAYED'")
    List<Film> findAllDisplayedFilms();

    @Query("select f from Film f where f.status='TEMPORARILY_UNAVAILABLE'")
    List<Film> findAllUnavailableFilms();





    @Query(value = "select f.* from film_entity f join watched_film_entity wf on f.id=wf.film_id where wf.user_id=:userId",
            nativeQuery = true)
    List<Film> findAllWatchedFilmsByUserId(@Param("userId") int userId);

    @Query(value = "select f.* from film_entity f join starred_film_entity sf on f.id = sf.film_id where sf.user_id=:userId",
            nativeQuery = true)
    List<Film> findAllStarredFilmsByUserId(@Param("userId") int userId);

    @Query(value = "select f.* from film_entity f join watched_film_entity wf on f.id = wf.film_id where wf.user_id=:userId and wf.film_id=:filmId",
            nativeQuery = true)
    Optional<Film> findWatchedFilmByUserIdAndFilmId(@Param("userId") int userId, @Param("filmId") int filmId);

    @Query(value = "select f from film_entity f join starred_film_entity sf on f.id = sf.film_id where sf.user_id=:userId and sf.film_id=:filmId",
            nativeQuery = true)
    Optional<Film> findStarredFilmByUserIdAndFilmId(@Param("userId") int userId, @Param("filmId") int filmId);

}
