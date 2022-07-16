package com.example.vfarmrdbackend.controller;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vfarmrdbackend.payload.MessageResponse;
import com.example.vfarmrdbackend.payload.ProjectRequest;
import com.example.vfarmrdbackend.service.ProjectService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Project", description = "The Project's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping("/projects/")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getAllProject() {
        try {
            return projectService.getAllProjects();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/projects/users/{user_id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getProjectByAssigned_user_id(@PathVariable("user_id") int user_id) {
        try {
            return projectService.getProjectByAssigned_user_id(user_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/projects/have-formula-status")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getProjectByStatus(
            @RequestParam(defaultValue = "", required = false) String formula_status) {
        try {
            return projectService.getProjectByFormula_status(formula_status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/projects/{project_id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getProjectByProject_id(@PathVariable("project_id") int project_id) {
        try {
            return projectService.getProjectByProject_id(project_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/projects/")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest request,
            @RequestHeader("Authorization") String jwt) {
        try {
            return projectService.createProject(request, jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/projects/{project_id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> updateProjects(@PathVariable("project_id") int project_id,
            @RequestBody ProjectRequest request, @RequestHeader("Authorization") String jwt) {
        try {
            return projectService.updateProject(project_id, request, jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/projects/{project_id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> deleteProject(@PathVariable("project_id") int project_id,
            @RequestHeader("Authorization") String jwt) {
        try {
            return projectService.deleteProject(project_id, jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

}
