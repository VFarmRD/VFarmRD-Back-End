package com.example.vfarmrdbackend.controller;

import java.util.List;

import com.example.vfarmrdbackend.model.Role;
import com.example.vfarmrdbackend.service.RoleService;

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
    public RoleService roleService;

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getAllRoles() {
        try {
            List<Role> listRoles = roleService.getAllRoles();
            if (listRoles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                        "Can't found any role!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(listRoles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    e.getMessage());
        }
    }

    @GetMapping("/roles/{role_id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getRoleByRole_id(@PathVariable("role_id") int role_id) {
        try {
            Role role = roleService.getRoleByRole_id(role_id);
            if (role != null) {
                return ResponseEntity.status(HttpStatus.OK).body(role);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    e.getMessage());
        }
    }

    // @PostMapping("/roles/create")
    // @PreAuthorize("hasAuthority('admin')")
    // public ResponseEntity<?> createRole(@RequestParam("role_name") String
    // role_name) {
    // try {
    // roleService.createRole(role_name);
    // return ResponseEntity.status(HttpStatus.OK).body("Create new role
    // completed!");

    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
    // e.getMessage());
    // }
    // }

    // @PutMapping("/roles/update")
    // @PreAuthorize("hasAuthority('admin')")
    // public ResponseEntity<?> updateRole(@RequestBody Role role) {
    // Role role = roleRepository.getRoleByRole_id(role.getRole_id());
    // if (role != null) {
    // role.setRole_name(role.getRole_name());
    // roleRepository.save(role);
    // return new ResponseEntity<>("Update role successfully!", HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
    // }

    // @DeleteMapping("/roles/delete/{role_id}")
    // @PreAuthorize("hasAuthority('admin')")
    // public ResponseEntity<?> deleteRole(@PathVariable("role_id") int role_id) {
    // try {
    // roleRepository.deleteById(role_id);
    // return new ResponseEntity<>("Delete role successfully!", HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(
    // e.getMessage(),
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }
}
