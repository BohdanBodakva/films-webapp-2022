package com.bohdan.films_webapp.services.implementation;

import com.bohdan.films_webapp.DAO.Comment;
import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.DAO.enums.CommentStatus;
import com.bohdan.films_webapp.exceptions.CommentNotFoundException;
import com.bohdan.films_webapp.exceptions.FilmNotFoundException;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;
import com.bohdan.films_webapp.repositories.CommentRepository;
import com.bohdan.films_webapp.repositories.FilmRepository;
import com.bohdan.films_webapp.repositories.UserRepository;
import com.bohdan.films_webapp.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Override
    public Set<Comment> getAllCommentsToFilmSortedByDateAsc(int filmId) throws FilmNotFoundException {
        filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + filmId + " wasn't found"));

        return commentRepository.findAll()
                .stream()
                .filter(comment -> comment.getFilm().getId() == filmId)
                .sorted(Comparator.comparing(Comment::getDate))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Comment> getAllCommentsToFilmSortedByDateDesc(int filmId) throws FilmNotFoundException {
        filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + filmId + " wasn't found"));

        return commentRepository.findAll()
                .stream()
                .filter(comment -> comment.getFilm().getId() == filmId)
                .sorted(Comparator.comparing(Comment::getDate).reversed())
                .collect(Collectors.toSet());
    }

    @Override
    public Comment getCommentById(int id) throws CommentNotFoundException {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id = " + id + " wasn't found"));
    }

    @Override
    public Comment saveComment(int userId, int filmId, Comment comment) throws UserNotFoundException, FilmNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " wasn't found"));
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + filmId + " wasn't found"));

        comment.setUser(user);
        comment.setFilm(film);
        comment.setStatus(CommentStatus.DISPLAYED);
        comment.setDate(LocalDateTime.now());

        user.getComments().add(comment);
        film.getComments().add(comment);

        userRepository.save(user);
        filmRepository.save(film);

        return commentRepository.save(comment);
    }

    @Override
    public Comment updateCommentById(int id, Comment comment) throws CommentNotFoundException {
        Comment commentToUpdate = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id = " + id + " wasn't found"));

        commentToUpdate.setComment(comment.getComment());

        return commentRepository.save(commentToUpdate);
    }

    @Override
    public Set<Comment> getAllCommentsByUserId(int userId) {
        return new HashSet<>(commentRepository.findCommentsByUserId(userId));
    }

    @Override
    public Set<Comment> getAllCommentsByFilmId(int filmId) {
        return new HashSet<>(commentRepository.findCommentsByFilmId(filmId));
    }

    @Override
    public void makeCommentDeletedByCommentIdAndUserIdAndFilmId(int id, int userId, int filmId) throws CommentNotFoundException, UserNotFoundException, FilmNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " wasn't found"));
        filmRepository.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException("Film with id = " + filmId + " wasn't found"));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id = " + id + " wasn't found"));

        comment.setStatus(CommentStatus.DELETED);

        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(int id) throws CommentNotFoundException {
        commentRepository.findById(id)
            .orElseThrow(() -> new CommentNotFoundException("Comment with id = " + id + " wasn't found"));
        commentRepository.deleteById(id);
    }
}
