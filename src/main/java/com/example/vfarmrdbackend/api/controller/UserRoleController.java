package com.example.vfarmrdbackend.api.controller;

import java.util.List;

import com.example.vfarmrdbackend.business.model.UserRole;
import com.example.vfarmrdbackend.data.repository.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class UserRoleController {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @GetMapping("/userrole")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<UserRole>> getAllUserRoles() {
        try {
            List<UserRole> _listUserRoles = userRoleRepository.findAll();
            if (_listUserRoles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_listUserRoles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
