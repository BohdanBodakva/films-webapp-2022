package com.bohdan.films_webapp.services.implementation;

import com.bohdan.films_webapp.DAO.Comment;
import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.DAO.enums.Role;
import com.bohdan.films_webapp.DAO.enums.UserStatus;
import com.bohdan.films_webapp.exceptions.EmailAlreadyExistsException;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;
import com.bohdan.films_webapp.repositories.CommentRepository;
import com.bohdan.films_webapp.repositories.FilmRepository;
import com.bohdan.films_webapp.repositories.UserRepository;
import com.bohdan.films_webapp.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final CommentRepository commentRepository;

    @Override
    public Set<User> getAllUsers() {
        return new HashSet<>(userRepository.findAll());
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " doesn't exist"));
    }

    @Override
    public User getUserByIdIfActive(int id) throws UserNotFoundException {
        return userRepository.findAll()
                .stream().filter(user -> user.getId() == id && user.getStatus().equals(UserStatus.ACTIVE))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(
                        "User with id = " + id + " doesn't exist or is banned/deleted"
                ));
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User with username = " + username + " doesn't exist"));
    }

    @Override
    public Set<User> getAllActiveUsers() {
        return new HashSet<>(userRepository.findAllActiveUsers());
    }

    @Override
    public Set<User> getAllBannedUsers() {
        return new HashSet<>(userRepository.findAllBannedUsers());
    }

    @Override
    public Set<User> getAllDeletedUsers() {
        return new HashSet<>(userRepository.findAllDeletedUsers());
    }

    @Override
    public User saveUser(User user){
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUserById(int id, User user) throws UserNotFoundException, EmailAlreadyExistsException {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " doesn't exist"));

//        if(!userRepository.findAll().stream()
//                .filter(u -> u.getId() != id && u.getEmail().equals(user.getEmail()))
//                .toList().isEmpty()){
//            throw new EmailAlreadyExistsException("Email = " + user.getEmail() + " already exists");
//        }

        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
//        userToUpdate.setPassword(
//                new BCryptPasswordEncoder(12).encode(user.getPassword())
//        );

        return userRepository.save(userToUpdate);
    }

    @Override
    public void makeUserDeletedById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " doesn't exist"));

        user.setStatus(UserStatus.DELETED);

        userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " doesn't exist"));

        for(Film film : user.getStarredFilms()){
            film.getUsersThatStarredTheFilm().remove(user);
        }
        for(Film film : user.getWatchedFilms()){
            film.getUsersThatWatchedTheFilm().remove(user);
        }
        for(Film film : filmRepository.findAll()){
            for(Comment comment : user.getComments()){
                film.getComments().remove(comment);
            }
        }

        commentRepository.deleteAllByUserId(user.getId());
        userRepository.deleteById(id);
    }

    @Override
    public void makeUserBannedById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " doesn't exist"));

        user.setStatus(UserStatus.BANNED);

        userRepository.save(user);
    }

    @Override
    public void makeUserActiveById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " doesn't exist"));

        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);
    }


}
