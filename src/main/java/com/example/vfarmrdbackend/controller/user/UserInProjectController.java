package com.example.vfarmrdbackend.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.user.UserInProjectService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserInProject", description = "The UserInProject's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class UserInProjectController {
    @Autowired
    UserInProjectService userInProjectService;

    @GetMapping("/userinproject/projects/{project_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> updateUser(@PathVariable("project_id") int project_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userInProjectService.getAllUserInProjectWithProject_id(project_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
