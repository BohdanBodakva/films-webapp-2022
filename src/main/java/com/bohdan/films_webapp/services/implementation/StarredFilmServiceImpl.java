package com.bohdan.films_webapp.services.implementation;

import com.bohdan.films_webapp.DAO.StarredFilm;
import com.bohdan.films_webapp.exception_handler.StarredFilmNotFoundException;
import com.bohdan.films_webapp.repositories.StarredFilmRepository;
import com.bohdan.films_webapp.services.StarredFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarredFilmServiceImpl implements StarredFilmService {
    private final StarredFilmRepository starredFilmRepository;

    @Autowired
    public StarredFilmServiceImpl(StarredFilmRepository starredFilmRepository) {
        this.starredFilmRepository = starredFilmRepository;
    }

    @Override
    public List<StarredFilm> getAllStarredFilms() {
        return starredFilmRepository.findAll();
    }

    @Override
    public StarredFilm getStarredFilmById(int id) throws StarredFilmNotFoundException {
        return starredFilmRepository.findById(id)
                .orElseThrow(() -> new StarredFilmNotFoundException("Starred film with id = " + id + " doesn't exist"));
    }

    @Override
    public void deleteStarredFilmById(int id) throws StarredFilmNotFoundException {
        starredFilmRepository.findById(id)
                .orElseThrow(() -> new StarredFilmNotFoundException("Starred film with id = " + id + " doesn't exist"));

        starredFilmRepository.deleteById(id);
    }

    @Override
    public List<StarredFilm> getStarredFilmsByUserId(int userId) {
        return starredFilmRepository.findStarredFilmsByUserId(userId);
    }

    @Override
    public List<StarredFilm> getStarredFilmsByFilmId(int filmId) {
        return starredFilmRepository.findStarredFilmsByFilmId(filmId);
    }
}
