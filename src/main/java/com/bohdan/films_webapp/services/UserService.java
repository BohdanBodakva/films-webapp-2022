package com.bohdan.films_webapp.services;

import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.exception_handler.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id) throws UserNotFoundException;
    User saveUser(User user);
    User updateUserById(int id, User user) throws UserNotFoundException;
    void deleteUserById(int id) throws UserNotFoundException;
    void banUserById(int id) throws UserNotFoundException;
    void makeUserActiveById(int id) throws UserNotFoundException;
}
