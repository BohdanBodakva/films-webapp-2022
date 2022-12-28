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

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Override
    public List<Comment> getAllCommentsSortedByDateAsc() {
        return commentRepository.findAll()
                .stream().sorted(Comparator.comparing(Comment::getDate))
                .toList();
    }

    @Override
    public List<Comment> getAllCommentsSortedByDateDesc() {
        return commentRepository.findAll()
                .stream().sorted(Comparator.comparing(Comment::getDate).reversed())
                .toList();
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
    public List<Comment> getAllCommentsByUserId(int userId) {
        return commentRepository.findCommentsByUserId(userId);
    }

    @Override
    public List<Comment> getAllCommentsByFilmId(int filmId) {
        return commentRepository.findCommentsByFilmId(filmId);
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
