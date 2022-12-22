package com.bohdan.films_webapp.services.implementation;

import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.DAO.enums.UserStatus;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;
import com.bohdan.films_webapp.exceptions.UserValidationException;
import com.bohdan.films_webapp.repositories.UserRepository;
import com.bohdan.films_webapp.services.UserService;
import com.bohdan.films_webapp.validation.CustomValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CustomValidation validation;


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
    public User saveUser(User user) throws UserValidationException {
        if(validation.validateUser(user)){
            user.setStatus(UserStatus.ACTIVE);
            return userRepository.save(user);
        }
        throw new UserValidationException("User with email = " + user.getEmail() + " wasn't validated");
    }

    @Override
    public User updateUserById(int id, User user) throws UserNotFoundException, UserValidationException {
        if(validation.validateUserForUpdating(user)){
            User userToUpdate = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " doesn't exist"));

            userToUpdate.setName(user.getName());
            userToUpdate.setSurname(user.getSurname());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setStatus(user.getStatus());

            return userRepository.save(userToUpdate);
        }
        throw new UserValidationException("User with email = " + user.getEmail() + " wasn't validated");
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
