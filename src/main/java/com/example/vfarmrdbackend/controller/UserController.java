package com.example.vfarmrdbackend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.example.vfarmrdbackend.model.User;
import com.example.vfarmrdbackend.payload.LoginRequest;
import com.example.vfarmrdbackend.payload.SignupRequest;
import com.example.vfarmrdbackend.payload.UserRequest;
import com.example.vfarmrdbackend.repository.UserRepository;
import com.example.vfarmrdbackend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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

@Tag(name = "User", description = "The User's API")
@RestController
@RequestMapping(path = "/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    Date date;

    @Operation(summary = "Login to system", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successfully!"),
            @ApiResponse(responseCode = "410", description = "User's account is disabled!"),
            @ApiResponse(responseCode = "500", description = "The server is down!")
    })
    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            if (userService.checkUserIsDisabled(loginRequest.getUser_name())) {
                return ResponseEntity.status(HttpStatus.GONE).body("Your account is disabled!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequest));
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @Operation(summary = "Create an account", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create account successfully!"),
            @ApiResponse(responseCode = "226", description = "This username is already registered!"),
            @ApiResponse(responseCode = "226", description = "This email is already registered!"),
            @ApiResponse(responseCode = "500", description = "The server is down!")
    })
    @PostMapping("/auth/create")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            if (userService.checkUser_nameIsExisted(signUpRequest.getUser_name())) {
                return ResponseEntity.status(HttpStatus.IM_USED).body(
                        "This username is already registered!");
            }
            if (userService.checkUser_nameIsExisted(signUpRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.IM_USED).body(
                        "This email is already registered!");
            }
            userService.register(signUpRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Create account completed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
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

    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getUserByUser_id(@PathVariable("user_id") int user_id) {
        try {
            User _user = userRepository.getUserByUser_id(user_id);
            if (_user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(_user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PutMapping("/users/update")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
        try {
            if (userService.updateUser(userRequest)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        "Update user successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PutMapping("/users/delete/{user_id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") int user_id) {
        try {
            if (userService.deleteUser(user_id)) {
                return ResponseEntity.status(HttpStatus.OK).body("Delete user successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PutMapping("/users/recover/{user_id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> recoverUser(@PathVariable("user_id") int user_id) {
        try {
            if (userService.recoverUser(user_id)) {
                return ResponseEntity.status(HttpStatus.OK).body("Recover user successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

}
