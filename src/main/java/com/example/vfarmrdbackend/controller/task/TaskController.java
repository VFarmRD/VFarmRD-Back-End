package com.example.vfarmrdbackend.controller.task;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vfarmrdbackend.payload.task.request.TaskCreateRequest;
import com.example.vfarmrdbackend.payload.task.request.TaskUpdateRequest;
import com.example.vfarmrdbackend.payload.others.response.MessageResponse;
import com.example.vfarmrdbackend.service.others.ValidatorService;
import com.example.vfarmrdbackend.service.task.TaskService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Task", description = "The Task's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class TaskController {
    @Autowired
    public TaskService taskService;

    @Autowired
    ValidatorService validatorService;

    @GetMapping("/tasks")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getAllTasks(@RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks("%", jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/tasks/{task_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> getTaskByTask_id(@PathVariable("task_id") int task_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskByTask_id(task_id, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/tasks")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> createTask(@RequestBody TaskCreateRequest taskCreateRequest,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (validatorService.validateString(taskCreateRequest.getTask_name())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Tên nhiệm vụ không hợp lệ!"));
            }
            if (validatorService.validateDate(taskCreateRequest.getStart_date(),
                    taskCreateRequest.getEstimated_date())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Ngày bắt đầu hoặc ngày dự tính không hợp lệ!"));
            }
            if (validatorService.validateString(taskCreateRequest.getDescription())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Ghi chú nhiệm vụ không hợp lệ!"));
            }
            taskService.createTask(taskCreateRequest, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Tạo nhiệm vụ thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/tasks/{task_id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> updateTask(@PathVariable("task_id") int task_id,
            @RequestBody TaskUpdateRequest taskUpdateRequest,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (validatorService.validateString(taskUpdateRequest.getTask_name())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Tên nhiệm vụ không hợp lệ!"));
            }
            if (validatorService.validateDate(taskUpdateRequest.getStart_date(),
                    taskUpdateRequest.getEstimated_date())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Ngày bắt đầu hoặc ngày dự tính không hợp lệ!"));
            }
            if (validatorService.validateString(taskUpdateRequest.getDescription())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Ghi chú nhiệm vụ không hợp lệ!"));
            }
            taskService.updateTask(task_id, taskUpdateRequest, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Cập nhật nhiệm vụ thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/tasks/{task_id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> deleteTask(@PathVariable("task_id") int task_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            taskService.deleteTask(task_id, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Xóa nhiệm vụ thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/tasks/statistics")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getTaskStatistics(
            @RequestParam(defaultValue = "none", required = false) String from_date,
            @RequestParam(defaultValue = "none", required = false) String to_date,
            @RequestParam(defaultValue = "0", required = false) int month,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (!from_date.equals("none") && !to_date.equals("none")) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        taskService.getTaskStatisticsFromDateToDate(jwt, from_date, to_date));
            } else if (month != 0 && year != 0) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        taskService.getTaskStatisticsWithMonthAndYear(jwt, month, year));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(
                        taskService.getTaskStatisticsOfAllTime(jwt));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/tasks/filter")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getAllTasksWithProject_idAndUser_id(
            @RequestHeader(required = false, value = "Authorization") String jwt,
            @RequestParam(defaultValue = "0", required = false) int project_id,
            @RequestParam(defaultValue = "0", required = false) int user_id,
            @RequestParam(defaultValue = "", required = false) String task_status) {
        try {
            if (project_id != 0 && user_id != 0) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(taskService.getAllTasksWithProject_idAndUser_id(project_id, user_id, task_status, jwt));
            } else if (user_id != 0) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(taskService.getAllTasksWithUser_id(user_id, task_status, jwt));
            } else if (project_id != 0) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(taskService.getAllTasksWithProject_id(project_id, task_status, jwt));
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(taskService.getAllTasks(task_status, jwt));

            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/tasks/{task_id}/change-status-is-done")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> setTaskStatusIfDone(@PathVariable("task_id") int task_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (taskService.setTaskStatusIfDone(task_id, jwt)) {
                return ResponseEntity.status(HttpStatus.OK).body("Set task status successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

}