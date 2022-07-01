package com.example.vfarmrdbackend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Project;
import com.example.vfarmrdbackend.payload.MessageResponse;
import com.example.vfarmrdbackend.payload.ProjectGetResponse;
import com.example.vfarmrdbackend.payload.ProjectRequest;
import com.example.vfarmrdbackend.repository.ProjectRepository;
import com.example.vfarmrdbackend.repository.UserRepository;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    Date date;

    public ResponseEntity<?> getAllProjects() {
        List<Project> listProjects = projectRepository.findAll();
        if (listProjects != null) {
            return ResponseEntity.status(HttpStatus.OK).body(listProjects);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy Dự Án nào!"));
        }
    }

    public ResponseEntity<?> getProjectByProject_id(String project_id) {
        Project project = projectRepository.getProjectByProject_id(project_id);
        if (project != null) {
            ProjectGetResponse response = new ProjectGetResponse();
            response.setProject_name(project.getProject_name());
            response.setClient_id(project.getClient_id());
            response.setCreated_user_id(project.getCreated_user_id());
            response.setCreated_user_name(userRepository.getUserByUser_id(project.getCreated_user_id()).getFullname());
            response.setAssigned_user_id(project.getAssigned_user_id());
            response.setAssigned_user_name(
                    userRepository.getUserByUser_id(project.getAssigned_user_id()).getFullname());
            response.setProject_code(project.getProject_code());
            response.setCreated_time(new Date());
            response.setComplete_date(project.getComplete_date());
            response.setProject_status(project.getProject_status());
            response.setRequirement(project.getRequirement());
            response.setEstimated_weight(project.getEstimated_weight());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy Dự Án!"));
        }
    }

    public ResponseEntity<?> createProject(ProjectRequest request, String jwt) {
        Project newProject = new Project();
        newProject.setProject_name(request.getProject_name());
        newProject.setClient_id(request.getClient_id());
        newProject.setCreated_user_id(JwtService.getUser_idFromToken(jwt));
        newProject.setAssigned_user_id(request.getAssigned_user_id());
        newProject.setProject_code(request.getProject_code());
        newProject.setCreated_time(new Date());
        newProject.setProject_status("running");
        newProject.setComplete_date(request.getComplete_date());
        newProject.setRequirement(request.getRequirement());
        newProject.setEstimated_weight(request.getEstimated_weight());
        projectRepository.save(newProject);
        return ResponseEntity.status(HttpStatus.OK).body(
                new MessageResponse("Thành công", "Tạo Dự Án thành công!"));
    }

    public ResponseEntity<?> updateProject(String project_id, ProjectRequest request) {
        Project updateProject = projectRepository.getProjectByProject_id(project_id);
        if (updateProject != null) {
            updateProject.setProject_name(request.getProject_name());
            updateProject.setClient_id(request.getClient_id());
            updateProject.setAssigned_user_id(request.getAssigned_user_id());
            updateProject.setProject_code(request.getProject_code());
            updateProject.setModified_time(new Date());
            updateProject.setComplete_date(request.getComplete_date());
            updateProject.setRequirement(request.getRequirement());
            updateProject.setEstimated_weight(request.getEstimated_weight());
            projectRepository.save(updateProject);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Chỉnh sửa Dự Án thành công!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy Dự Án!"));
        }
    }

    public ResponseEntity<?> deleteProject(String project_id) {
        Project project = projectRepository.getProjectByProject_id(project_id);
        if (project != null) {
            project.setProject_status("canceled");
            project.setModified_time(new Date());
            projectRepository.save(project);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Xóa Dự Án thành công!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy Dự Án!"));
        }
    }
}
