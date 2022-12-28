package com.bohdan.films_webapp.services.implementation;

import com.bohdan.films_webapp.DAO.Comment;
import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DAO.User;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " doesn't exist"));
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email = " + email + " doesn't exist"));
    }

    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.findAllActiveUsers();
    }

    @Override
    public List<User> getAllBannedUsers() {
        return userRepository.findAllBannedUsers();
    }

    @Override
    public List<User> getAllDeletedUsers() {
        return userRepository.findAllDeletedUsers();
    }

    @Override
    public User saveUser(User user){
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public User updateUserById(int id, User user) throws UserNotFoundException, EmailAlreadyExistsException {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " doesn't exist"));

        if(!userRepository.findAll().stream()
                .filter(u -> u.getId() != id && u.getEmail().equals(user.getEmail()))
                .toList().isEmpty()){
            throw new EmailAlreadyExistsException("Email = " + user.getEmail() + " already exists");
        }

        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(
                new BCryptPasswordEncoder(12).encode(user.getPassword())
        );

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
