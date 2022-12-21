package com.bohdan.films_webapp.repositories;

import com.bohdan.films_webapp.DAO.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
}
