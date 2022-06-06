package com.example.vfarmrdbackend.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.vfarmrdbackend.business.model.Role;
import com.example.vfarmrdbackend.business.model.User;
import com.example.vfarmrdbackend.business.model.UserRole;
import com.example.vfarmrdbackend.business.payload.LoginRequest;
import com.example.vfarmrdbackend.business.payload.SignupRequest;
import com.example.vfarmrdbackend.business.payload.UserRequest;
import com.example.vfarmrdbackend.business.service.UserService;
import com.example.vfarmrdbackend.data.repository.RoleRepository;
import com.example.vfarmrdbackend.data.repository.UserRepository;
import com.example.vfarmrdbackend.data.repository.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    Date date;

    @Autowired
    UserService userService;

    @PostMapping("/users/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        if (userService.checkUserIsDisabled(loginRequest.getUser_name())) {
            return new ResponseEntity<>(
                    "Your account is disabled!",
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                userService.login(loginRequest),
                HttpStatus.OK);
    }

    @PostMapping("/users/create")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userService.checkUser_nameIsExisted(signUpRequest.getUser_name())) {
            return new ResponseEntity<>(
                    "This username is already registered!",
                    HttpStatus.IM_USED);
        }
        if (userService.checkUser_nameIsExisted(signUpRequest.getEmail())) {
            return new ResponseEntity<>(
                    "This email is already registered!",
                    HttpStatus.IM_USED);
        }
        return new ResponseEntity<>("Sign up account completed!", HttpStatus.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "", required = false) String user_name,
            @RequestParam(defaultValue = "", required = false) String email,
            @RequestParam(defaultValue = "", required = false) String fullname,
            @RequestParam(defaultValue = "", required = false) String phone,
            @RequestParam(defaultValue = "", required = false) String role_name,
            @RequestParam(defaultValue = "true", required = false) boolean user_status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size) {
        try {
            List<User> _listUsers = new ArrayList<User>();
            Pageable paging = PageRequest.of(page, size);
            Page<User> pageUsers;
            pageUsers = userRepository.findUserByFields("%" + user_name + "%",
                    "%" + email + "%", "%" + fullname + "%", "%" + phone + "%",
                    "%" + role_name + "%", user_status, paging);
            _listUsers = pageUsers.getContent();
            // Map<String, Object> response = new HashMap<>();
            // response.put("users", _listUsers);
            // response.put("currentPage", pageUsers.getNumber());
            // response.put("totalItems", pageUsers.getTotalElements());
            // response.put("totalPages", pageUsers.getTotalPages());
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
        User _user = userRepository.getUserByUser_id(id);
        if (_user != null) {
            return new ResponseEntity<>(_user, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/update")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
        if (userService.updateUser(userRequest)) {
            return new ResponseEntity<>("Update user successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/delete/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>("Delete user successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/recover/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> recoverUser(@PathVariable("id") int id) {
        if (userService.recoverUser(id)) {
            return new ResponseEntity<>("Recover user successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
