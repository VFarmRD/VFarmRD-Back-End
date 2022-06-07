package com.example.vfarmrdbackend.api.controller;

import java.util.List;

import com.example.vfarmrdbackend.business.model.Role;
import com.example.vfarmrdbackend.business.service.RoleService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Role", description = "The Role's API")
@RestController
@RequestMapping(path = "/api")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('admin')")
    private ResponseEntity<?> getAllRoles() {
        try {
            List<Role> _listRoles = roleService.getAllRoles();
            if (_listRoles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                        "Can't found any role!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    _listRoles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @GetMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('admin')")
    private ResponseEntity<?> getRoleByRole_id(@PathVariable("id") int role_id) {
        try {
            Role _role = roleService.getRoleByRole_id(role_id);
            if (_role != null) {
                return ResponseEntity.status(HttpStatus.OK).body(_role);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    // @PostMapping("/roles/create")
    // @PreAuthorize("hasAuthority('admin')")
    // private ResponseEntity<?> createRole(@RequestParam("role_name") String role_name) {
    //     try {
    //         roleService.createRole(role_name);
    //         return ResponseEntity.status(HttpStatus.OK).body("Create new role completed!");

    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
    //                 "The server is down!");
    //     }
    // }

    // @PutMapping("/roles/update")
    // @PreAuthorize("hasAuthority('admin')")
    // private ResponseEntity<?> updateRole(@RequestBody Role role) {
    //     Role _role = roleRepository.getRoleByRole_id(role.getRole_id());
    //     if (_role != null) {
    //         _role.setRole_name(role.getRole_name());
    //         roleRepository.save(_role);
    //         return new ResponseEntity<>("Update role successfully!", HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

    // @DeleteMapping("/roles/delete/{id}")
    // @PreAuthorize("hasAuthority('admin')")
    // private ResponseEntity<?> deleteRole(@PathVariable("id") int id) {
    //     try {
    //         roleRepository.deleteById(id);
    //         return new ResponseEntity<>("Delete role successfully!", HttpStatus.OK);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(
    //                 "The server is down!",
    //                 HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}
