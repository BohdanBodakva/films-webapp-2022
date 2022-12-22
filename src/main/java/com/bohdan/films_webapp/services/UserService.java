package com.bohdan.films_webapp.services;

import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;
import com.bohdan.films_webapp.exceptions.UserValidationException;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id) throws UserNotFoundException;
    User saveUser(User user) throws UserValidationException;
    User updateUserById(int id, User user) throws UserNotFoundException, UserValidationException;
    void deleteUserById(int id) throws UserNotFoundException;
    void banUserById(int id) throws UserNotFoundException;
    void makeUserActiveById(int id) throws UserNotFoundException;
}
