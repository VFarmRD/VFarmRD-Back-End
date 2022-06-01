package com.example.vfarmrdbackend.controllers;

import java.util.Date;
import java.util.List;

import com.example.vfarmrdbackend.models.User;
import com.example.vfarmrdbackend.models.UserRole;
import com.example.vfarmrdbackend.payload.UserRequest;
import com.example.vfarmrdbackend.repositories.RoleRepository;
import com.example.vfarmrdbackend.repositories.UserRepository;
import com.example.vfarmrdbackend.repositories.UserRoleRepository;

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
    private UserRoleRepository urrepo;

    @Autowired
    private RoleRepository rolerepo;

    @Autowired
    PasswordEncoder encoder;

    Date date = new Date();

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> _listUsers = repo.getAllUsers();
            for (int i = 0; i < _listUsers.size(); i++) {
                _listUsers.get(i).setRole_name(repo.getHighestRoleWithUser_Id(_listUsers.get(i).getUser_id()));
            }
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
            _user.setRole_name(repo.getHighestRoleWithUser_Id(_user.getUser_id()));
            return new ResponseEntity<>(_user, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/update")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
        User _user = repo.getUserByUser_id(userRequest.getUser_id());
        UserRole _userrole = urrepo.getUserRoleByUser_id(userRequest.getUser_id());
        if (_user != null && _userrole != null) {
            int role_id = rolerepo.getRoleByRole_name(userRequest.getRole_name()).getRole_id();
            _userrole.setRole_id(role_id);
            _user.setEmail(userRequest.getEmail());
            _user.setFullname(userRequest.getFullname());
            _user.setPhone(userRequest.getPhone());
            _user.setRole_name(repo.getHighestRoleWithUser_Id(_user.getUser_id()));
            _user.setPassword(encoder.encode(userRequest.getPassword()));
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

    @PutMapping("/users/recover/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> recoverUser(@PathVariable("id") int id) {
        User _user = repo.getUserByUser_id(id);
        if (_user != null) {
            _user.setUser_status(true);
            repo.save(_user);
            return new ResponseEntity<>("Recover user successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
