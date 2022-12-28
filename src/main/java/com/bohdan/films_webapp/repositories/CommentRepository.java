package com.bohdan.films_webapp.repositories;

import com.bohdan.films_webapp.DAO.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByUserId(int userId);
    List<Comment> findCommentsByFilmId(int filmId);
    void deleteAllByUserId(int userId);
    void deleteAllByFilmId(int filmId);
}
