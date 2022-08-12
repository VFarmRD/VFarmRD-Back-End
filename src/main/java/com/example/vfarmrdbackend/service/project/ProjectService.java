package com.example.vfarmrdbackend.service.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.vfarmrdbackend.service.user.UserInProjectService;
import com.example.vfarmrdbackend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.log.Log;
import com.example.vfarmrdbackend.model.notification.Notification;
import com.example.vfarmrdbackend.model.project.Project;
import com.example.vfarmrdbackend.model.error.ErrorModel;
import com.example.vfarmrdbackend.payload.project.ProjectRequest;
import com.example.vfarmrdbackend.payload.project.ProjectStatisticsFromDateToDateResponse;
import com.example.vfarmrdbackend.payload.project.ProjectStatisticsResponse;
import com.example.vfarmrdbackend.payload.project.ProjectGetResponse;
import com.example.vfarmrdbackend.repository.project.ProjectRepository;
import com.example.vfarmrdbackend.service.error.ErrorService;
import com.example.vfarmrdbackend.service.log.LogService;
import com.example.vfarmrdbackend.service.notification.NotificationService;
import com.example.vfarmrdbackend.service.others.JwtService;

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

    @Autowired
    UserInProjectService userInProjectService;

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
            response.setListUserInProject(userInProjectService
                    .getAllUserInProjectWithProject_id(project.getProject_id()));
            response.setProject_code(project.getProject_code());
            response.setCreated_time(project.getCreated_time());
            response.setStart_date(project.getStart_date());
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

    public List<ProjectGetResponse> getProjectByUser_id(int user_id, String project_status,
            String jwt) {
        try {
            List<Project> listProject = projectRepository.getProjectByUser_id(user_id,
                    "%" + project_status + "%");
            List<ProjectGetResponse> listResponse = new ArrayList<>();
            for (int i = 0; i < listProject.size(); i++) {
                Project project = listProject.get(i);
                if (new Date().after(project.getStart_date())) {
                    ProjectGetResponse response = new ProjectGetResponse();
                    response.setProject_id(project.getProject_id());
                    response.setProject_name(project.getProject_name());
                    response.setClient_id(project.getClient_id());
                    response.setCreated_user_id(project.getCreated_user_id());
                    response.setCreated_user_name(userService.getUserInfo(project.getCreated_user_id()).getFullname());
                    response.setListUserInProject(userInProjectService
                            .getAllUserInProjectWithProject_id(project.getProject_id()));
                    response.setProject_code(project.getProject_code());
                    response.setCreated_time(project.getCreated_time());
                    response.setStart_date(project.getStart_date());
                    response.setComplete_date(project.getComplete_date());
                    response.setProject_status(project.getProject_status());
                    response.setRequirement(project.getRequirement());
                    response.setEstimated_weight(project.getEstimated_weight());
                    listResponse.add(response);
                }
            }
            return listResponse;
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT GET BY USER_ID",
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
                response.setListUserInProject(userInProjectService
                        .getAllUserInProjectWithProject_id(project.getProject_id()));
                response.setProject_code(project.getProject_code());
                response.setCreated_time(project.getCreated_time());
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
            newProject.setProject_code(request.getProject_code());
            newProject.setCreated_time(new Date());
            newProject.setProject_status("running");
            newProject.setStart_date(request.getStart_date());
            newProject.setComplete_date(request.getComplete_date());
            newProject.setRequirement(request.getRequirement());
            newProject.setEstimated_weight(request.getEstimated_weight());
            projectRepository.save(newProject);
            for (int i = 0; i < request.getListUser_id().size(); i++) {
                userInProjectService.createUserInProject(
                        request.getListUser_id().get(i),
                        projectRepository.getProjectByProject_code(request.getProject_code()).getProject_id());
                notificationService.createNotification(new Notification(
                        request.getListUser_id().get(i),
                        "Thông báo",
                        "Bạn đã được phân công vào dự án " + request.getProject_name() + " !",
                        new Date()));
            }

            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "PROJECT",
                    "CREATE",
                    String.valueOf(
                            projectRepository.getProjectByProject_code(request.getProject_code()).getProject_id()),
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
            updateProject.setProject_code(request.getProject_code());
            updateProject.setModified_time(new Date());
            updateProject.setStart_date(request.getStart_date());
            updateProject.setComplete_date(request.getComplete_date());
            updateProject.setRequirement(request.getRequirement());
            updateProject.setEstimated_weight(request.getEstimated_weight());
            projectRepository.save(updateProject);
            List<Integer> listUserInProjectOld = userInProjectService.getAllUser_idInProjectWithProject_id(project_id);
            List<Integer> listUserInProjectNew = request.getListUser_id();
            for (int i = 0; i < listUserInProjectNew.size(); i++) {
                for (int j = 0; j < listUserInProjectOld.size(); j++) {
                    if (listUserInProjectNew.get(i) == listUserInProjectOld.get(j)) {
                        listUserInProjectNew.remove(Integer.valueOf(listUserInProjectNew.get(i)));
                        listUserInProjectOld.remove(Integer.valueOf(listUserInProjectOld.get(j)));
                    }
                }
            }
            if (listUserInProjectOld.size() != 0) {
                for (int i = 0; i < listUserInProjectOld.size(); i++) {
                    userInProjectService.deleteUserInProject(
                            userInProjectService.getUserInProjectWithUser_idAndProject_id(listUserInProjectOld.get(i),
                                    project_id).getUip_id());
                }
            }
            if (listUserInProjectNew.size() != 0) {
                for (int i = 0; i < listUserInProjectNew.size(); i++) {
                    userInProjectService.createUserInProject(listUserInProjectNew.get(i), project_id);
                }
            }
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
            userInProjectService.createUserInProject(user_id, project_id);
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

    public List<ProjectGetResponse> getProjectByMaterial_id(String material_id, String jwt) {
        try {
            List<Project> listProject = projectRepository.getProjectByMaterial_id(material_id);
            List<ProjectGetResponse> listResponse = new ArrayList<>();
            for (int i = 0; i < listProject.size(); i++) {
                Project project = listProject.get(i);
                ProjectGetResponse response = new ProjectGetResponse();
                response.setProject_id(project.getProject_id());
                response.setProject_name(project.getProject_name());
                response.setClient_id(project.getClient_id());
                response.setCreated_user_id(project.getCreated_user_id());
                response.setCreated_user_name(userService.getUserInfo(project.getCreated_user_id()).getFullname());
                response.setListUserInProject(userInProjectService
                        .getAllUserInProjectWithProject_id(project.getProject_id()));
                response.setProject_code(project.getProject_code());
                response.setCreated_time(project.getCreated_time());
                response.setStart_date(project.getStart_date());
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
                    "PROJECT GET ALL BY MATERIAL ID",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public ProjectStatisticsResponse getProjectStatisticsOfAllTime(String jwt) {
        try {
            return new ProjectStatisticsResponse(projectRepository.getTotalProject(),
                    projectRepository.getTotalProjectRunning(),
                    projectRepository.getTotalProjectCanceled(),
                    projectRepository.getTotalProjectHaveProduct());
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT STATISTIC OF ALL TIME",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public List<ProjectStatisticsFromDateToDateResponse> getProjectStatisticsFromDateToDate(String jwt,
            String from_date, String to_date) {
        try {
            LocalDate start = LocalDate.parse(from_date.split(" ")[0]);
            LocalDate end = LocalDate.parse(to_date.split(" ")[0]);
            List<LocalDate> totalDates = new ArrayList<>();
            while (!start.isAfter(end)) {
                totalDates.add(start);
                start = start.plusDays(1);
            }
            List<ProjectStatisticsFromDateToDateResponse> listResponses = new ArrayList<>();
            for (int i = 0; i < listResponses.size(); i++) {
                if (projectRepository.getTotalProjectFromDateToDate(totalDates.get(i) + " 00:00:00",
                        totalDates.get(i) + " 23:59:59") != 0) {
                    listResponses.add(new ProjectStatisticsFromDateToDateResponse(listResponses.get(i).toString(),
                            projectRepository.getTotalProjectFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59"),
                            projectRepository.getTotalProjectRunningFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59"),
                            projectRepository.getTotalProjectCanceledFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59"),
                            projectRepository.getTotalProjectHaveProductFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59")));
                }
            }
            return listResponses;
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT STATISTIC FROM DATE TO DATE",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public ProjectStatisticsResponse getProjectStatisticsWithMonthAndYear(String jwt, int month, int year) {
        try {
            return new ProjectStatisticsResponse(projectRepository.getTotalProjectWithMonthAndYear(month, year),
                    projectRepository.getTotalProjectRunningWithMonthAndYear(month, year),
                    projectRepository.getTotalProjectCanceledWithMonthAndYear(month, year),
                    projectRepository.getTotalProjectHaveProductWithMonthAndYear(month, year));
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PROJECT STATISTIC WITH MONTH AND YEAR",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public List<ProjectGetResponse> getProjectByClient_id(String client_id, String jwt) {
        try {
            List<Project> listProject = projectRepository.getProjectByClient_id(client_id);
            List<ProjectGetResponse> listResponse = new ArrayList<>();
            for (int i = 0; i < listProject.size(); i++) {
                Project project = listProject.get(i);
                ProjectGetResponse response = new ProjectGetResponse();
                response.setProject_id(project.getProject_id());
                response.setProject_name(project.getProject_name());
                response.setClient_id(project.getClient_id());
                response.setCreated_user_id(project.getCreated_user_id());
                response.setCreated_user_name(userService.getUserInfo(project.getCreated_user_id()).getFullname());
                response.setListUserInProject(userInProjectService
                        .getAllUserInProjectWithProject_id(project.getProject_id()));
                response.setProject_code(project.getProject_code());
                response.setCreated_time(project.getCreated_time());
                response.setStart_date(project.getStart_date());
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
                    "PROJECT GET BY CLIENT_ID",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }
}
