package com.bohdan.films_webapp.services.implementation;

import com.bohdan.films_webapp.DAO.Film;
import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.DAO.enums.UserStatus;
import com.bohdan.films_webapp.exception_handler.FilmNotFoundException;
import com.bohdan.films_webapp.exception_handler.UserNotFoundException;
import com.bohdan.films_webapp.repositories.UserRepository;
import com.bohdan.films_webapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public User saveUser(User user) {
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public User updateUserById(int id, User user) throws UserNotFoundException {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " doesn't exist"));

        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setStatus(user.getStatus());

        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUserById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " doesn't exist"));

        user.setStatus(UserStatus.DELETED);

        userRepository.save(user);
    }

    @Override
    public void banUserById(int id) throws UserNotFoundException {
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
