package com.example.vfarmrdbackend.controller.role;

import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.role.RoleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class RoleController {
    @Autowired
    public RoleService roleService;

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getAllRoles() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(roleService.getAllRoles());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/roles/{role_id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getRoleByRole_id(@PathVariable("role_id") int role_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleByRole_id(role_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
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
    // new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
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
    // new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"),
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }
}
