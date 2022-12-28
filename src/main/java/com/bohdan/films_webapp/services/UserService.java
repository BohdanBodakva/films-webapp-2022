package com.bohdan.films_webapp.services;

import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.exceptions.EmailAlreadyExistsException;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    List<User> getAllActiveUsers();
    List<User> getAllBannedUsers();
    List<User> getAllDeletedUsers();
    User saveUser(User user) ;
    User updateUserById(int id, User user) throws UserNotFoundException, EmailAlreadyExistsException;
    void makeUserDeletedById(int id) throws UserNotFoundException;
    void deleteUserById(int id) throws UserNotFoundException;
    void makeUserBannedById(int id) throws UserNotFoundException;
    void makeUserActiveById(int id) throws UserNotFoundException;
}
