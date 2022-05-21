package com.example.vfarmrdbackend.controllers;

import java.util.List;

import com.example.vfarmrdbackend.models.Role;
import com.example.vfarmrdbackend.payload.response.MessageResponse;
import com.example.vfarmrdbackend.repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class RoleController {
    @Autowired
    private RoleRepository repo;

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getAllRoles() {
        try {
            List<Role> _listRoles = repo.findAll();
            if (_listRoles.isEmpty()) {
                return new ResponseEntity<>(
                        new MessageResponse("List Role is null!"),
                        HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_listRoles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new MessageResponse("The server is downnnn!!!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
