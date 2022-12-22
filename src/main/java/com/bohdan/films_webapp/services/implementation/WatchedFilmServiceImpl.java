package com.bohdan.films_webapp.services.implementation;

import com.bohdan.films_webapp.DAO.WatchedFilm;
import com.bohdan.films_webapp.exceptions.WatchedFilmNotFoundException;
import com.bohdan.films_webapp.repositories.WatchedFilmRepository;
import com.bohdan.films_webapp.services.WatchedFilmService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WatchedFilmServiceImpl implements WatchedFilmService {
    private final WatchedFilmRepository watchedFilmRepository;

    @Override
    public List<WatchedFilm> getAllWatchedFilms() {
        return watchedFilmRepository.findAll();
    }

    @Override
    public WatchedFilm getWatchedFilmById(int id) throws WatchedFilmNotFoundException {
        return watchedFilmRepository.findById(id)
                .orElseThrow(() -> new WatchedFilmNotFoundException("Watched film with id = " + id + " doesn't exist"));
    }

    @Override
    public void deleteWatchedFilmById(int id) throws WatchedFilmNotFoundException {
        watchedFilmRepository.findById(id)
                .orElseThrow(() -> new WatchedFilmNotFoundException("Watched film with id = " + id + " doesn't exist"));

        watchedFilmRepository.deleteById(id);
    }

    @Override
    public List<WatchedFilm> getWatchedFilmsByUserId(int userId) {
        return watchedFilmRepository.findWatchedFilmsByUserId(userId);
    }

    @Override
    public List<WatchedFilm> getWatchedFilmsByFilmId(int filmId) {
        return watchedFilmRepository.findWatchedFilmsByFilmId(filmId);
    }
}
