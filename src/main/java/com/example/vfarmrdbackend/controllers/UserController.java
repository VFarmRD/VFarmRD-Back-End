package com.example.vfarmrdbackend.controllers;

import java.util.Date;
import java.util.List;

import com.example.vfarmrdbackend.models.User;
import com.example.vfarmrdbackend.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class UserController {
    @Autowired
    private UserRepository repo;

    @Autowired
    PasswordEncoder encoder;

    Date date = new Date();

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
    public ResponseEntity<?> getUserByUser_id(@PathVariable("id") int id) {
        User _user = repo.getUserByUser_id(id);
        if (_user != null) {
            return new ResponseEntity<>(_user, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/update/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateUser(@RequestBody() int user_id,
            @RequestBody String email,
            @RequestBody String fullname,
            @RequestBody String phone,
            @RequestBody String password) {
        User _user = repo.getUserByUser_id(user_id);
        if (_user != null) {
            _user.setEmail(email);
            _user.setFullname(fullname);
            _user.setPhone(phone);
            _user.setPassword(encoder.encode(password));
            _user.setModified_time(date);
            repo.save(_user);
            return new ResponseEntity<>("Update user successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/delete/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        User _user = repo.getUserByUser_id(id);
        if (_user != null) {
            _user.setUser_status(false);
            repo.save(_user);
            return new ResponseEntity<>("Delete user successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}