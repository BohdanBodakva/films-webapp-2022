package com.bohdan.films_webapp.controllers;

import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.exceptions.UserNotFoundException;
import com.bohdan.films_webapp.exceptions.UserValidationException;
import com.bohdan.films_webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id){
        try {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        try {
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
        } catch (UserValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable("id") int id,
                                               @RequestBody User user){
        try {
            return new ResponseEntity<>(userService.updateUserById(id, user), HttpStatus.OK);
        } catch (UserNotFoundException | UserValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") int id){
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
