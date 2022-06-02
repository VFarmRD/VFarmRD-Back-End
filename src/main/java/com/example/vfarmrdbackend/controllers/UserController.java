package com.example.vfarmrdbackend.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.vfarmrdbackend.models.Role;
import com.example.vfarmrdbackend.models.User;
import com.example.vfarmrdbackend.models.UserRole;
import com.example.vfarmrdbackend.payload.UserRequest;
import com.example.vfarmrdbackend.repositories.RoleRepository;
import com.example.vfarmrdbackend.repositories.UserRepository;
import com.example.vfarmrdbackend.repositories.UserRoleRepository;

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
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    Date date;

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
            if (user_name != null || email != null || fullname != null ||
                    phone != null || role_name != null) {
                pageUsers = userRepository.findUserByFields("%" + user_name + "%",
                        "%" + email + "%", "%" + fullname + "%", "%" + phone + "%",
                        "%" + role_name + "%", user_status, paging);
            } else {
                pageUsers = userRepository.findAllUsers(paging);
            }
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

    void upgradeUserRoleToAdmin(int user_id, String role_name) {
        List<Role> listRole = roleRepository.getAllRoles();
        for (int i = 0; i < listRole.size(); i++) {
            if (listRole.get(i).getRole_id() != roleRepository.getRoleByRole_name(role_name)
                    .getRole_id()) {
                UserRole newuserrole = new UserRole();
                newuserrole.setUser_id(user_id);
                newuserrole.setRole_id(listRole.get(i).getRole_id());
                userRoleRepository.save(newuserrole);
            }
        }
    }

    void degradeUserRoleFromAdmin(int user_id, String role_name) {
        List<UserRole> listUserRole = userRoleRepository.getAllRoleOfOneByUser_id(user_id);
        for (int i = 0; i < listUserRole.size(); i++) {
            if (listUserRole.get(i).getRole_id() != roleRepository.getRoleByRole_name(role_name)
                    .getRole_id()) {
                userRoleRepository.delete(listUserRole.get(i));
            }
        }
    }

    @PutMapping("/users/update")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
        date = new Date();
        User _user = userRepository.getUserByUser_id(userRequest.getUser_id());
        UserRole _userrole = userRoleRepository.getUserRoleByUser_id(userRequest.getUser_id());
        int change_role_id = roleRepository.getRoleByRole_name(userRequest.getRole_name()).getRole_id();
        if (_user != null && _userrole != null) {
            if (_user.getRole_name().equals("admin") && !userRequest.getRole_name().equals("admin")) {
                degradeUserRoleFromAdmin(userRequest.getUser_id(), userRequest.getRole_name());
            } else if (!_user.getRole_name().equals("admin") && userRequest.getRole_name().equals("admin")) {
                upgradeUserRoleToAdmin(userRequest.getUser_id(), userRequest.getRole_name());
            } else {
                _userrole.setRole_id(change_role_id);
                userRoleRepository.save(_userrole);
            }
            _user.setEmail(userRequest.getEmail());
            _user.setFullname(userRequest.getFullname());
            _user.setPhone(userRequest.getPhone());
            _user.setRole_name(userRoleRepository.getHighestRoleWithUser_Id(_user.getUser_id()));
            _user.setPassword(encoder.encode(userRequest.getPassword()));
            _user.setModified_time(date);
            userRepository.save(_user);
            return new ResponseEntity<>("Update user successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/delete/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        User _user = userRepository.getUserByUser_id(id);
        if (_user != null) {
            _user.setUser_status(false);
            userRepository.save(_user);
            return new ResponseEntity<>("Delete user successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/recover/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> recoverUser(@PathVariable("id") int id) {
        User _user = userRepository.getUserByUser_id(id);
        if (_user != null) {
            _user.setUser_status(true);
            userRepository.save(_user);
            return new ResponseEntity<>("Recover user successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
