package com.bohdan.films_webapp.DTO;

import com.bohdan.films_webapp.DAO.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CommentDto {
    private int id;
    private int user;
    private int film;
    private String comment;
    private LocalDateTime date;

    public CommentDto(int id, int user, int film, String comment, LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.film = film;
        this.comment = comment;
        this.date = date;
    }

    public static CommentDto fromComment(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getFilm().getId(),
                comment.getComment(),
                comment.getDate()
        );
    }

    public static Set<CommentDto> fromCommentSet(Set<Comment> comments){
        return comments.stream()
                .map(CommentDto::fromComment)
                .collect(Collectors.toSet());
    }
}
