package com.example.vfarmrdbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.ErrorModel;
import com.example.vfarmrdbackend.model.Log;
import com.example.vfarmrdbackend.model.Notification;
import com.example.vfarmrdbackend.model.Project;
import com.example.vfarmrdbackend.payload.request.ProjectRequest;
import com.example.vfarmrdbackend.payload.response.ProjectGetResponse;
import com.example.vfarmrdbackend.repository.ProjectRepository;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    LogService logService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    ErrorService errorService;

    @Autowired
    UserService userService;

    public List<Project> getAllProjects(String jwt) {
        try {
            return projectRepository.findAll();
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT GET ALL",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public ProjectGetResponse getProjectByProject_id(int project_id, String jwt) {
        try {
            Project project = projectRepository.getProjectByProject_id(project_id);
            ProjectGetResponse response = new ProjectGetResponse();
            response.setProject_id(project.getProject_id());
            response.setProject_name(project.getProject_name());
            response.setClient_id(project.getClient_id());
            response.setCreated_user_id(project.getCreated_user_id());
            response.setCreated_user_name(userService.getUserInfo(project.getCreated_user_id()).getFullname());
            response.setAssigned_user_id(project.getAssigned_user_id());
            response.setAssigned_user_name(userService.getUserInfo(project.getAssigned_user_id()).getFullname());
            response.setProject_code(project.getProject_code());
            response.setCreated_time(new Date());
            response.setComplete_date(project.getComplete_date());
            response.setProject_status(project.getProject_status());
            response.setRequirement(project.getRequirement());
            response.setEstimated_weight(project.getEstimated_weight());
            return response;
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT GET BY PROJECT ID",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public List<ProjectGetResponse> getProjectByAssigned_user_id(int assigned_user_id, String project_status,
            String jwt) {
        try {
            List<Project> listProject = projectRepository.getProjectByAssigned_user_id(assigned_user_id,
                    "%" + project_status + "%");
            List<ProjectGetResponse> listResponse = new ArrayList<>();
            for (int i = 0; i < listProject.size(); i++) {
                Project project = listProject.get(i);
                ProjectGetResponse response = new ProjectGetResponse();
                response.setProject_id(project.getProject_id());
                response.setProject_name(project.getProject_name());
                response.setClient_id(project.getClient_id());
                response.setCreated_user_id(project.getCreated_user_id());
                response.setCreated_user_name(userService.getUserInfo(project.getCreated_user_id()).getFullname());
                response.setAssigned_user_id(project.getAssigned_user_id());
                response.setAssigned_user_name(userService.getUserInfo(project.getAssigned_user_id()).getFullname());
                response.setProject_code(project.getProject_code());
                response.setCreated_time(new Date());
                response.setComplete_date(project.getComplete_date());
                response.setProject_status(project.getProject_status());
                response.setRequirement(project.getRequirement());
                response.setEstimated_weight(project.getEstimated_weight());
                listResponse.add(response);
            }
            return listResponse;
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT GET BY ASSIGNED_USER_ID",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public List<ProjectGetResponse> getProjectByFormula_status(String formula_status, String user_id, String jwt) {
        try {
            List<Project> listProject = projectRepository.getProjectByFormula_status("%" + formula_status + "%",
                    "%" + user_id + "%");
            List<ProjectGetResponse> listResponse = new ArrayList<>();
            for (int i = 0; i < listProject.size(); i++) {
                Project project = listProject.get(i);
                ProjectGetResponse response = new ProjectGetResponse();
                response.setProject_id(project.getProject_id());
                response.setProject_name(project.getProject_name());
                response.setClient_id(project.getClient_id());
                response.setCreated_user_id(project.getCreated_user_id());
                response.setCreated_user_name(userService.getUserInfo(project.getCreated_user_id()).getFullname());
                response.setAssigned_user_id(project.getAssigned_user_id());
                response.setAssigned_user_name(userService.getUserInfo(project.getAssigned_user_id()).getFullname());
                response.setProject_code(project.getProject_code());
                response.setCreated_time(new Date());
                response.setComplete_date(project.getComplete_date());
                response.setProject_status(project.getProject_status());
                response.setRequirement(project.getRequirement());
                response.setEstimated_weight(project.getEstimated_weight());
                listResponse.add(response);
            }
            return listResponse;
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT GET BY FORMULA STATUS",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public void createProject(ProjectRequest request, String jwt) {
        try {
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
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "PROJECT",
                    "CREATE",
                    String.valueOf(
                            projectRepository.getProjectByProject_code(newProject.getProject_code()).getProject_id()),
                    new Date()));
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT CREATE",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public void updateProject(int project_id, ProjectRequest request, String jwt) {
        try {
            Project updateProject = projectRepository.getProjectByProject_id(project_id);
            updateProject.setProject_name(request.getProject_name());
            updateProject.setClient_id(request.getClient_id());
            updateProject.setAssigned_user_id(request.getAssigned_user_id());
            updateProject.setProject_code(request.getProject_code());
            updateProject.setModified_time(new Date());
            updateProject.setComplete_date(request.getComplete_date());
            updateProject.setRequirement(request.getRequirement());
            updateProject.setEstimated_weight(request.getEstimated_weight());
            projectRepository.save(updateProject);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "PROJECT",
                    "UPDATE",
                    String.valueOf(project_id),
                    new Date()));
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT UPDATE",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public void deleteProject(int project_id, String jwt) {
        try {
            Project project = projectRepository.getProjectByProject_id(project_id);
            project.setProject_status("canceled");
            project.setModified_time(new Date());
            projectRepository.save(project);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "PROJECT",
                    "DELETE",
                    String.valueOf(project_id),
                    new Date()));
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT DELETE",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public void recoverProject(int project_id, String jwt) {
        try {
            Project project = projectRepository.getProjectByProject_id(project_id);
            project.setProject_status("running");
            project.setModified_time(new Date());
            projectRepository.save(project);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "PROJECT",
                    "RECOVER",
                    String.valueOf(project_id),
                    new Date()));
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT RECOVER",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public void assignUserFromTaskToProject(int project_id, int user_id) {
        try {
            Project project = projectRepository.getProjectByProject_id(project_id);
            project.setAssigned_user_id(user_id);
            projectRepository.save(project);
            notificationService.createNotification(new Notification(
                    user_id,
                    "Thông báo",
                    "Bạn đã được phân công vào 1 dự án!",
                    new Date()));
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    0,
                    "PROJECT ASSIGN USER FROM TASK",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

}
