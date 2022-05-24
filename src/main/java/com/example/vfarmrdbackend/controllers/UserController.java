package com.example.vfarmrdbackend.controllers;

import java.util.List;

import com.example.vfarmrdbackend.models.User;
import com.example.vfarmrdbackend.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class UserController {
    @Autowired
    private UserRepository repo;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> _listUsers = repo.getAllUsers();
            if (_listUsers.isEmpty()) {
                return new ResponseEntity<>(
                        "Can't found any user!",
                        HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_listUsers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id) {
        User _user = repo.getUserByUser_id(id);
        if (_user != null) {
            return new ResponseEntity<>(_user, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
    }

    // update user info
    // @PutMapping("/users/{id}")
    // public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody
    // User user) {
    // User _user = repo.findUserByUser_id(id);
    // if (user != null) {
    // _user.setUser_name(user_name);

    // return new ResponseEntity<>(repo.save(_user), HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
    // }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
