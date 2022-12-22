package com.bohdan.films_webapp.services.implementation;

import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DAO.enums.FilmStatus;
import com.bohdan.films_webapp.exceptions.FilmNotFoundException;
import com.bohdan.films_webapp.exceptions.FilmValidationException;
import com.bohdan.films_webapp.repositories.FilmRepository;
import com.bohdan.films_webapp.services.FilmService;
import com.bohdan.films_webapp.validation.CustomValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final CustomValidation validation;

    @Override
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @Override
    public List<Film> getAllAvailableFilms() {
        return filmRepository.findAll()
                .stream().filter(film -> film.getStatus().equals(FilmStatus.DISPLAYED))
                .toList();
    }

    @Override
    public Film getFilmById(int id) throws FilmNotFoundException {
        return filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + id + " doesn't exist"));
    }

    @Override
    public Film getFilmByIdIfAvailable(int id) throws FilmNotFoundException {
        List<Film> films = filmRepository.findAll()
                .stream().filter(film -> film.getId() == id && film.getStatus().equals(FilmStatus.DISPLAYED))
                .toList();

        if(films.isEmpty()){
            throw new FilmNotFoundException("Film with id = " + id + " doesn't exist or is unavailable");
        }

        return films.get(0);
    }


    @Override
    public Film saveFilm(Film film) throws FilmValidationException {
        if(validation.validateFilm(film)){
            return filmRepository.save(film);
        }
        throw new FilmValidationException("Film with name = " + film.getName() + " wasn't validated");
    }

    @Override
    public Film updateFilmById(int id, Film film) throws FilmNotFoundException, FilmValidationException {
        if(validation.validateFilmForUpdating(film)) {
            Film filmToUpdate = filmRepository.findById(id)
                    .orElseThrow(() -> new FilmNotFoundException("Film with id = " + id + " doesn't exist"));

            filmToUpdate.setName(film.getName());
            filmToUpdate.setYear(film.getYear());
            filmToUpdate.setImageName(film.getImageName());
            filmToUpdate.setDescription(film.getDescription());
            filmToUpdate.setStatus(film.getStatus());

            return filmRepository.save(filmToUpdate);
        }
        throw new FilmValidationException("Film with name = " + film.getName() + " wasn't validated");
    }

    @Override
    public void deleteFilmById(int id) throws FilmNotFoundException {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + id + " doesn't exist"));

        film.setStatus(FilmStatus.DELETED);

        filmRepository.save(film);
    }

    @Override
    public void makeFilmUnavailableById(int id) throws FilmNotFoundException {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + id + " doesn't exist"));

        film.setStatus(FilmStatus.TEMPORARILY_UNAVAILABLE);

        filmRepository.save(film);
    }

    @Override
    public void makeFilmDisplayedById(int id) throws FilmNotFoundException {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + id + " doesn't exist"));

        film.setStatus(FilmStatus.DISPLAYED);

        filmRepository.save(film);
    }
}
