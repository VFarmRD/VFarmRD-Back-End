package com.example.vfarmrdbackend.controller.project;

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

import com.example.vfarmrdbackend.payload.project.request.ProjectRequest;
import com.example.vfarmrdbackend.payload.others.response.MessageResponse;
import com.example.vfarmrdbackend.service.others.ValidatorService;
import com.example.vfarmrdbackend.service.project.ProjectService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Project", description = "The Project's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    ValidatorService validatorService;

    @GetMapping("/projects/")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getAllProject(@RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllProjects(jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/projects/users/{user_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getProjectByUser_id(@PathVariable("user_id") int user_id,
            @RequestParam(defaultValue = "", required = false) String project_status,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(projectService.getProjectByUser_id(user_id, project_status, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/projects/have-formula-status")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getProjectByStatus(
            @RequestParam(defaultValue = "", required = false) String formula_status,
            @RequestParam(defaultValue = "", required = false) String user_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(projectService.getProjectByFormula_status(formula_status, user_id, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/projects/{project_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getProjectByProject_id(@PathVariable("project_id") int project_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectByProject_id(project_id, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/projects/")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest request,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (validatorService.validateString(request.getProject_name())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Tên dự án không hợp lệ!"));
            }
            if (validatorService.validateString(request.getProject_code())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Mã dự án không hợp lệ!"));
            }
            if (validatorService.validateDate(request.getStart_date(), request.getComplete_date())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Ngày bắt đầu hoặc ngày hoàn thành không hợp lệ!"));
            }
            if (validatorService.validateString(request.getRequirement())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Yêu cầu dự án không hợp lệ!"));
            }
            if (validatorService.validateFloat(request.getEstimated_weight())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Khối lượng dự án không hợp lệ!"));
            }
            projectService.createProject(request, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Tạo Dự Án mới thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/projects/{project_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> updateProjects(@PathVariable("project_id") int project_id,
            @RequestBody ProjectRequest request, @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (validatorService.validateString(request.getProject_name())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Tên dự án không hợp lệ!"));
            }
            if (validatorService.validateString(request.getProject_code())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Mã dự án không hợp lệ!"));
            }
            if (validatorService.validateDate(request.getStart_date(), request.getComplete_date())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Ngày bắt đầu hoặc ngày hoàn thành không hợp lệ!"));
            }
            if (validatorService.validateString(request.getRequirement())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Yêu cầu dự án không hợp lệ!"));
            }
            if (validatorService.validateFloat(request.getEstimated_weight())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Khối lượng dự án không hợp lệ!"));
            }
            projectService.updateProject(project_id, request, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Cập nhật Dự Án thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/projects/{project_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> deleteProject(@PathVariable("project_id") int project_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            projectService.deleteProject(project_id, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Xóa Dự Án thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/projects/{project_id}/recover-project")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> recoverProject(@PathVariable("project_id") int project_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            projectService.recoverProject(project_id, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Khôi phục Dự Án thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/projects/materials/{material_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getProjectByMaterial_id(@PathVariable("material_id") String material_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(projectService.getProjectByMaterial_id(material_id, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/projects/statistics")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getProjectStatistics(
            @RequestParam(defaultValue = "none", required = false) String from_date,
            @RequestParam(defaultValue = "none", required = false) String to_date,
            @RequestParam(defaultValue = "0", required = false) int month,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (!from_date.equals("none") && !to_date.equals("none")) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        projectService.getProjectStatisticsFromDateToDate(jwt, from_date, to_date));
            } else if (month != 0 && year != 0) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        projectService.getProjectStatisticsWithMonthAndYear(jwt, month, year));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(
                        projectService.getProjectStatisticsOfAllTime(jwt));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/projects/clients/{client_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getProjectByClient_id(@PathVariable("client_id") String client_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(projectService.getProjectByClient_id(client_id, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
