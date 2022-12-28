package com.bohdan.films_webapp.DTO;

import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DAO.User;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
    private String status;
    private Set<Integer> starredFilms;
    private Set<Integer> watchedFilms;
    private Set<CommentDto> comments;

    public UserDto(int id,
                   String name,
                   String surname,
                   String email,
                   String password,
                   String role,
                   String status,
                   Set<Integer> starredFilms,
                   Set<Integer> watchedFilms,
                   Set<CommentDto> comments) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.starredFilms = starredFilms;
        this.watchedFilms = watchedFilms;
        this.comments = comments;
    }

    public static UserDto fromUser(User user){
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name(),
                user.getStatus().name(),
                user.getStarredFilms()
                        .stream().map(Film::getId).collect(Collectors.toSet()),
                user.getWatchedFilms()
                        .stream().map(Film::getId).collect(Collectors.toSet()),
                CommentDto.fromCommentSet(user.getComments())
        );
    }

    public static Set<UserDto> fromUserList(List<User> users){
        return users.stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toSet());
    }
}
