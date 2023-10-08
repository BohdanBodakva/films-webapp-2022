package com.bohdan.films_webapp.services;

import com.bohdan.films_webapp.DAO.Comment;
import com.bohdan.films_webapp.exceptions.CommentNotFoundException;
import com.bohdan.films_webapp.exceptions.FilmNotFoundException;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface CommentService {
    Set<Comment> getAllCommentsToFilmSortedByDateAsc(int filmId) throws FilmNotFoundException;
    Set<Comment> getAllCommentsToFilmSortedByDateDesc(int filmId) throws FilmNotFoundException;
    Comment getCommentById(int id) throws CommentNotFoundException;
    Comment saveComment(int userId, int filmId, Comment comment) throws UserNotFoundException, FilmNotFoundException;
    Comment updateCommentById(int id, Comment comment) throws CommentNotFoundException;
    Set<Comment> getAllCommentsByUserId(int userId);
    Set<Comment> getAllCommentsByFilmId(int filmId);
    void makeCommentDeletedByCommentIdAndUserIdAndFilmId(int id, int userId, int filmId) throws CommentNotFoundException, UserNotFoundException, FilmNotFoundException;
    void deleteCommentById(int id) throws CommentNotFoundException;
}
