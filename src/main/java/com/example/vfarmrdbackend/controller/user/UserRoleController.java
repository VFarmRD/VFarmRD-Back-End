package com.example.vfarmrdbackend.controller.user;

import java.util.List;

import com.example.vfarmrdbackend.model.user.UserRole;
import com.example.vfarmrdbackend.repository.user.UserRoleRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Role", description = "The User Role's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class UserRoleController {
    @Autowired
    UserRoleRepository userRoleRepository;

    @GetMapping("/userrole")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<UserRole>> getAllUserRoles() {
        try {
            List<UserRole> listUserRoles = userRoleRepository.findAll();
            if (listUserRoles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listUserRoles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
