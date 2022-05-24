package com.example.vfarmrdbackend.controllers;

import java.util.List;

import com.example.vfarmrdbackend.models.Role;
import com.example.vfarmrdbackend.repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                        "Can't found any role!",
                        HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_listRoles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getRoleById(@PathVariable("id") int id) {
        Role _role = repo.getRoleByRole_id(id);
        if (_role != null) {
            return new ResponseEntity<>(_role, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Role not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        try {
            repo.save(role);
            return new ResponseEntity<>(
                    "Create new role completed!",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateRole(@PathVariable("id") int id, @RequestBody Role role) {
        Role _role = repo.getRoleByRole_id(id);
        if (_role != null) {
            _role.setRole_name(role.getRole_name());
            repo.save(_role);
            return new ResponseEntity<>("Update role successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteRole(@PathVariable("id") int id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>("Delete role successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
