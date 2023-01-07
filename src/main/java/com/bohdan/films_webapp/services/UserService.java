package com.bohdan.films_webapp.services;

import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.exceptions.EmailAlreadyExistsException;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;

import java.util.Set;

public interface UserService {
    Set<User> getAllUsers();
    User getUserById(int id) throws UserNotFoundException;
    User getUserByIdIfActive(int id) throws UserNotFoundException;
    User getUserByUsername(String email) throws UserNotFoundException;
    Set<User> getAllActiveUsers();
    Set<User> getAllBannedUsers();
    Set<User> getAllDeletedUsers();
    User saveUser(User user) ;
    User updateUserById(int id, User user) throws UserNotFoundException, EmailAlreadyExistsException;
    void makeUserDeletedById(int id) throws UserNotFoundException;
    void deleteUserById(int id) throws UserNotFoundException;
    void makeUserBannedById(int id) throws UserNotFoundException;
    void makeUserActiveById(int id) throws UserNotFoundException;
}
