package com.bohdan.films_webapp.services.implementation;

import com.bohdan.films_webapp.DAO.Comment;
import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.DAO.enums.FilmStatus;
import com.bohdan.films_webapp.exceptions.FilmNotFoundException;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;
import com.bohdan.films_webapp.repositories.CommentRepository;
import com.bohdan.films_webapp.repositories.FilmRepository;
import com.bohdan.films_webapp.repositories.UserRepository;
import com.bohdan.films_webapp.services.FilmService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public Set<Film> getAllFilms() {
        return new HashSet<>(filmRepository.findAll());
    }

    @Override
    public Set<Film> getAllWatchedFilmsByUserId(int userId) throws UserNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Film with id = " + userId + " doesn't exist"));
        return new HashSet<>(filmRepository.findAllWatchedFilmsByUserId(userId));
    }

    @Override
    public Set<Film> getAllStarredFilmsByUserId(int userId) throws UserNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " doesn't exist"));
        return new HashSet<>(filmRepository.findAllStarredFilmsByUserId(userId));
    }

    @Override
    public Film getWatchedFilmByUserIdAndFilmId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " doesn't exist"));
        filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + userId + " doesn't exist"));

        return filmRepository.findWatchedFilmByUserIdAndFilmId(userId, filmId).get();
    }

    @Override
    public Film getStarredFilmByUserIdAndFilmId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " doesn't exist"));
        filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + userId + " doesn't exist"));

        return filmRepository.findStarredFilmByUserIdAndFilmId(userId, filmId).get();
    }

    @Override
    public void makeFilmWatchedByFilmIdAndUserId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " doesn't exist"));
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + filmId + " doesn't exist"));

        film.getUsersThatWatchedTheFilm().add(user);
        user.getWatchedFilms().add(film);

        filmRepository.save(film);
        userRepository.save(user);
    }

    @Override
    public void makeFilmStarredByFilmIdAndUserId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " doesn't exist"));
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + userId + " doesn't exist"));

        film.getUsersThatStarredTheFilm().add(user);
        user.getStarredFilms().add(film);

        filmRepository.save(film);
        userRepository.save(user);
    }

    @Override
    public void makeFilmUnstarredByFilmIdAndUserId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " doesn't exist"));
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + userId + " doesn't exist"));

        film.getUsersThatStarredTheFilm().remove(user);
        user.getStarredFilms().remove(film);

        filmRepository.save(film);
        userRepository.save(user);
    }

    @Override
    public Set<Film> getAllDisplayedFilms() {
        return new HashSet<>(filmRepository.findAllDisplayedFilms());
    }

    @Override
    public Set<Film> getAllDeletedFilms() {
        return new HashSet<>(filmRepository.findAllDeletedFilms());
    }

    @Override
    public Set<Film> getAllUnavailableFilms() {
        return new HashSet<>(filmRepository.findAllUnavailableFilms());
    }

    @Override
    public Film getFilmById(int id) throws FilmNotFoundException {
        return filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + id + " doesn't exist"));
    }

    @Override
    public Film getFilmByIdIfDisplayed(int id) throws FilmNotFoundException {
        List<Film> films = filmRepository.findAll()
                .stream().filter(film -> film.getId() == id && film.getStatus().equals(FilmStatus.DISPLAYED))
                .toList();

        if(films.isEmpty()){
            throw new FilmNotFoundException("Film with id = " + id + " doesn't exist or is deleted/unavailable");
        }

        return films.get(0);
    }


    @Override
    public Film saveFilm(Film film)  {
        film.setStatus(FilmStatus.DISPLAYED);
        return filmRepository.save(film);
    }

    @Override
    public Film updateFilmById(int id, Film film) throws FilmNotFoundException {
        Film filmToUpdate = filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + id + " doesn't exist"));

        filmToUpdate.setName(film.getName());
        filmToUpdate.setYear(film.getYear());
        filmToUpdate.setImageName(film.getImageName());
        filmToUpdate.setDescription(film.getDescription());

        return filmRepository.save(filmToUpdate);
    }

    @Override
    public void makeFilmDeletedById(int id) throws FilmNotFoundException {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + id + " doesn't exist"));

        film.setStatus(FilmStatus.DELETED);

        filmRepository.save(film);
    }

    @Override
    public void deleteFilmById(int id) throws FilmNotFoundException {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + id + " doesn't exist"));

        for(User user : film.getUsersThatStarredTheFilm()){
            user.getStarredFilms().remove(film);
        }
        for(User user : film.getUsersThatWatchedTheFilm()){
            user.getWatchedFilms().remove(film);
        }
        for(User user : userRepository.findAll()){
            for(Comment comment : film.getComments()){
                user.getComments().remove(comment);
            }
        }

        commentRepository.deleteAllByFilmId(film.getId());
        filmRepository.deleteById(id);
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

    @Override
    public void deleteAllWatchedFilmsByUserId(int userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " wasn't found"));

        user.getWatchedFilms().clear();

        userRepository.save(user);
    }

    @Override
    public void deleteAllStarredFilmsByUserId(int userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " wasn't found"));

        user.getStarredFilms().clear();

        userRepository.save(user);
    }

    @Override
    public void deleteStarredFilmByFilmIdAndUserId(int userId, int filmId) throws UserNotFoundException, FilmNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " wasn't found"));
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + filmId + " doesn't exist"));

        user.getStarredFilms().remove(film);
        film.getUsersThatStarredTheFilm().remove(user);

        filmRepository.save(film);
        userRepository.save(user);
    }
}
