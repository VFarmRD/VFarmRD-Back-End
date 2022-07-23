package com.example.vfarmrdbackend.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.vfarmrdbackend.model.Task;
import com.example.vfarmrdbackend.payload.request.TaskCreateRequest;
import com.example.vfarmrdbackend.payload.request.TaskUpdateRequest;
import com.example.vfarmrdbackend.payload.response.MessageResponse;
import com.example.vfarmrdbackend.payload.response.TaskGetResponse;
import com.example.vfarmrdbackend.service.JwtService;
import com.example.vfarmrdbackend.service.TaskService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Task", description = "The Task's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class TaskController {
    @Autowired
    public TaskService taskService;

    @GetMapping("/tasks")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getAllTasks(@RequestHeader("Authorization") String jwt) {
        try {
            List<TaskGetResponse> listTasks = taskService.getAllTasks(JwtService.getUser_idFromToken(jwt));
            if (listTasks != null) {
                return ResponseEntity.status(HttpStatus.OK).body(listTasks);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found any task!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/tasks/{task_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> getTaskByTask_id(@PathVariable("task_id") int task_id) {
        try {
            Task task = taskService.getTaskByTask_id(task_id);
            if (task != null) {
                return ResponseEntity.status(HttpStatus.OK).body(task);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found any task!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/tasks")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> createTask(@RequestBody TaskCreateRequest taskCreateRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            taskService.createTask(taskCreateRequest, jwt);
            return ResponseEntity.status(HttpStatus.OK).body("Create Task successfully!!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/tasks/{task_id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> updateTask(@PathVariable("task_id") int task_id,
            @RequestBody TaskUpdateRequest taskUpdateRequest, @RequestHeader("Authorization") String jwt) {
        try {
            if (taskService.updateTask(task_id, taskUpdateRequest, jwt)) {
                return ResponseEntity.status(HttpStatus.OK).body("Update task successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/tasks/{task_id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> deleteTask(@PathVariable("task_id") int task_id,
            @RequestHeader("Authorization") String jwt) {
        try {
            if (taskService.deleteTask(task_id, jwt)) {
                return ResponseEntity.status(HttpStatus.OK).body("Delete task successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
