package com.example.vfarmrdbackend.service.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.vfarmrdbackend.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.error.ErrorModel;
import com.example.vfarmrdbackend.model.log.Log;
import com.example.vfarmrdbackend.model.notification.Notification;
import com.example.vfarmrdbackend.model.task.Task;
import com.example.vfarmrdbackend.model.user.User;
import com.example.vfarmrdbackend.payload.project.ProjectGetResponse;
import com.example.vfarmrdbackend.payload.task.TaskCreateRequest;
import com.example.vfarmrdbackend.payload.task.TaskUpdateRequest;
import com.example.vfarmrdbackend.payload.task.TaskGetResponse;
import com.example.vfarmrdbackend.payload.task.TaskStatisticsFromDateToDateResponse;
import com.example.vfarmrdbackend.payload.task.TaskStatisticsResponse;
import com.example.vfarmrdbackend.repository.task.TaskRepository;
import com.example.vfarmrdbackend.repository.user.UserRepository;
import com.example.vfarmrdbackend.service.error.ErrorService;
import com.example.vfarmrdbackend.service.log.LogService;
import com.example.vfarmrdbackend.service.notification.NotificationService;
import com.example.vfarmrdbackend.service.others.JwtService;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    LogService logService;

    @Autowired
    ProjectService projectService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    ErrorService errorService;

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    @Scheduled(cron = "0 0 0 * * ?")
    public void setTask_statusIfOvertime() {
        Date date = new Date();
        List<Task> listTasks = taskRepository.findAll();
        for (int i = 0; i < listTasks.size(); i++) {
            Task task = listTasks.get(i);
            Date beforeDeadline1Day = new Date(task.getEstimated_date().getTime() - MILLIS_IN_A_DAY);
            if (!task.getTask_status().equals("done") &&
                    date == beforeDeadline1Day) {
                notificationService.createNotification(new Notification(
                        task.getUser_id(),
                        "Thông báo",
                        "Còn 1 ngày nữa là tới deadline!",
                        new Date()));
            }
            if (date.after(task.getEstimated_date()) &&
                    !task.getTask_status().equals("done")) {
                task.setTask_status("overtime");
            }
            if (date == task.getStart_date()) {
                projectService.assignUserFromTaskToProject(
                        task.getProject_id(), task.getUser_id());
            }
            taskRepository.save(task);
        }
    }

    public List<TaskGetResponse> getAllTasks(String task_status, String jwt) {
        List<Task> listTasks = new ArrayList<>();
        List<TaskGetResponse> listTasksResponse = new ArrayList<>();
        User requestUser = userRepository.getUserByUser_id(JwtService.getUser_idFromToken(jwt));
        if (requestUser.getRole_name().equals("staff")) {
            listTasks = taskRepository.getAllTasksWithUser_id(JwtService.getUser_idFromToken(jwt),
                    "%" + task_status + "%");
        } else {
            listTasks = taskRepository.getAllTasksWithTask_status("%" + task_status + "%");
        }
        for (int i = 0; i < listTasks.size(); i++) {
            Task task = listTasks.get(i);
            TaskGetResponse newTaskInfo = new TaskGetResponse();
            ProjectGetResponse project = projectService.getProjectByProject_id(task.getProject_id(), jwt);
            newTaskInfo.setTask_id(task.getTask_id());
            newTaskInfo.setTask_name(task.getTask_name());
            newTaskInfo.setUser_id(task.getUser_id());
            newTaskInfo.setUser_name(requestUser.getUser_name());
            newTaskInfo.setUser_role(requestUser.getRole_name());
            newTaskInfo.setProject_id(task.getProject_id());
            newTaskInfo.setProject_name(project.getProject_name());
            newTaskInfo.setCreated_date(task.getCreated_date());
            newTaskInfo.setStart_date(task.getStart_date());
            newTaskInfo.setEstimated_date(task.getEstimated_date());
            newTaskInfo.setTask_status(task.getTask_status());
            newTaskInfo.setDescription(task.getDescription());
            listTasksResponse.add(newTaskInfo);
        }
        return listTasksResponse;
    }

    public List<TaskGetResponse> getAllTasksWithUser_id(int user_id, String task_status, String jwt) {
        List<Task> listTasks = taskRepository.getAllTasksWithUser_id(user_id, "%" + task_status + "%");
        List<TaskGetResponse> listTasksResponse = new ArrayList<>();
        for (int i = 0; i < listTasks.size(); i++) {
            Task task = listTasks.get(i);
            TaskGetResponse newTaskInfo = new TaskGetResponse();
            User user = userRepository.getUserByUser_id(task.getUser_id());
            ProjectGetResponse project = projectService.getProjectByProject_id(task.getProject_id(), jwt);
            newTaskInfo.setTask_id(task.getTask_id());
            newTaskInfo.setTask_name(task.getTask_name());
            newTaskInfo.setUser_id(task.getUser_id());
            newTaskInfo.setUser_name(user.getUser_name());
            newTaskInfo.setUser_role(user.getRole_name());
            newTaskInfo.setProject_id(task.getProject_id());
            newTaskInfo.setProject_name(project.getProject_name());
            newTaskInfo.setCreated_date(task.getCreated_date());
            newTaskInfo.setStart_date(task.getStart_date());
            newTaskInfo.setEstimated_date(task.getEstimated_date());
            newTaskInfo.setTask_status(task.getTask_status());
            newTaskInfo.setDescription(task.getDescription());
            listTasksResponse.add(newTaskInfo);
        }
        return listTasksResponse;
    }

    public List<TaskGetResponse> getAllTasksWithProject_id(int project_id, String task_status, String jwt) {
        List<Task> listTasks = taskRepository.getAllTasksWithProject_id(project_id, "%" + task_status + "%");
        List<TaskGetResponse> listTasksResponse = new ArrayList<>();
        for (int i = 0; i < listTasks.size(); i++) {
            Task task = listTasks.get(i);
            TaskGetResponse newTaskInfo = new TaskGetResponse();
            User user = userRepository.getUserByUser_id(task.getUser_id());
            ProjectGetResponse project = projectService.getProjectByProject_id(task.getProject_id(), jwt);
            newTaskInfo.setTask_id(task.getTask_id());
            newTaskInfo.setTask_name(task.getTask_name());
            newTaskInfo.setUser_id(task.getUser_id());
            newTaskInfo.setUser_name(user.getUser_name());
            newTaskInfo.setUser_role(user.getRole_name());
            newTaskInfo.setProject_id(task.getProject_id());
            newTaskInfo.setProject_name(project.getProject_name());
            newTaskInfo.setCreated_date(task.getCreated_date());
            newTaskInfo.setStart_date(task.getStart_date());
            newTaskInfo.setEstimated_date(task.getEstimated_date());
            newTaskInfo.setTask_status(task.getTask_status());
            newTaskInfo.setDescription(task.getDescription());
            listTasksResponse.add(newTaskInfo);
        }
        return listTasksResponse;
    }

    public List<TaskGetResponse> getAllTasksWithProject_idAndUser_id(int project_id, int user_id, String task_status,
            String jwt) {
        List<Task> listTasks = taskRepository.getAllTasksWithProject_idAndUser_id(project_id, user_id,
                "%" + task_status + "%");
        List<TaskGetResponse> listTasksResponse = new ArrayList<>();
        for (int i = 0; i < listTasks.size(); i++) {
            Task task = listTasks.get(i);
            TaskGetResponse newTaskInfo = new TaskGetResponse();
            User user = userRepository.getUserByUser_id(task.getUser_id());
            ProjectGetResponse project = projectService.getProjectByProject_id(task.getProject_id(), jwt);
            newTaskInfo.setTask_id(task.getTask_id());
            newTaskInfo.setTask_name(task.getTask_name());
            newTaskInfo.setUser_id(task.getUser_id());
            newTaskInfo.setUser_name(user.getUser_name());
            newTaskInfo.setUser_role(user.getRole_name());
            newTaskInfo.setProject_id(task.getProject_id());
            newTaskInfo.setProject_name(project.getProject_name());
            newTaskInfo.setCreated_date(task.getCreated_date());
            newTaskInfo.setStart_date(task.getStart_date());
            newTaskInfo.setEstimated_date(task.getEstimated_date());
            newTaskInfo.setTask_status(task.getTask_status());
            newTaskInfo.setDescription(task.getDescription());
            listTasksResponse.add(newTaskInfo);
        }
        return listTasksResponse;
    }

    public Task getTaskByTask_id(int task_id) {
        return taskRepository.getTaskByTask_id(task_id);
    }

    public void createTask(TaskCreateRequest taskCreateRequest, String jwt) {
        Task newTask = new Task();
        newTask.setTask_name(taskCreateRequest.getTask_name());
        newTask.setUser_id(taskCreateRequest.getUser_id());
        newTask.setProject_id(taskCreateRequest.getProject_id());
        newTask.setCreated_date(new Date());
        newTask.setStart_date(taskCreateRequest.getStart_date());
        newTask.setEstimated_date(taskCreateRequest.getEstimated_date());
        newTask.setDescription(taskCreateRequest.getDescription());
        newTask.setTask_status("doing");
        taskRepository.save(newTask);
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String dateString = df.format(taskCreateRequest.getStart_date());
        notificationService.createNotification(new Notification(
                taskCreateRequest.getUser_id(),
                "Thông báo",
                "Bạn đã được phân công để làm 1 công thúc vào " + dateString,
                new Date()));
        logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                "TASK",
                "CREATE",
                String.valueOf(taskRepository.getNewestTaskByProject_id(newTask.getProject_id()).getTask_id()),
                new Date()));
    }

    public boolean updateTask(int task_id, TaskUpdateRequest taskUpdateRequest, String jwt) {
        Task updateTask = taskRepository.getTaskByTask_id(task_id);
        if (updateTask != null) {
            updateTask.setTask_name(taskUpdateRequest.getTask_name());
            updateTask.setUser_id(taskUpdateRequest.getUser_id());
            updateTask.setStart_date(taskUpdateRequest.getStart_date());
            updateTask.setEstimated_date(taskUpdateRequest.getEstimated_date());
            updateTask.setDescription(taskUpdateRequest.getDescription());
            updateTask.setTask_status(taskUpdateRequest.getTask_status());
            taskRepository.save(updateTask);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "TASK",
                    "UPDATE",
                    String.valueOf(task_id),
                    new Date()));
            return true;
        }
        return false;
    }

    public boolean deleteTask(int task_id, String jwt) {
        Task deleteTask = taskRepository.getTaskByTask_id(task_id);
        if (deleteTask != null) {
            deleteTask.setTask_status("deleted");
            taskRepository.save(deleteTask);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "TASK",
                    "DELETE",
                    String.valueOf(task_id),
                    new Date()));
            return true;
        }
        return false;
    }

    public TaskStatisticsResponse getTaskStatisticsOfAllTime(String jwt) {
        try {
            return new TaskStatisticsResponse(taskRepository.getTotalTask(),
                    taskRepository.getTotalTaskDoing(),
                    taskRepository.getTotalTaskDone(),
                    taskRepository.getTotalTaskOvertime());
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "TASK STATISTIC OF ALL TIME",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public List<TaskStatisticsFromDateToDateResponse> getTaskStatisticsFromDateToDate(String jwt, String from_date,
            String to_date) {
        try {
            LocalDate start = LocalDate.parse(from_date.split(" ")[0]);
            LocalDate end = LocalDate.parse(to_date.split(" ")[0]);
            List<LocalDate> totalDates = new ArrayList<>();
            while (!start.isAfter(end)) {
                totalDates.add(start);
                start = start.plusDays(1);
            }
            List<TaskStatisticsFromDateToDateResponse> listResponses = new ArrayList<>();
            for (int i = 0; i < listResponses.size(); i++) {
                if (taskRepository.getTotalTaskFromDateToDate(totalDates.get(i) + " 00:00:00",
                        totalDates.get(i) + " 23:59:59") != 0) {
                    listResponses.add(new TaskStatisticsFromDateToDateResponse(listResponses.get(i).toString(),
                            taskRepository.getTotalTaskFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59"),
                            taskRepository.getTotalTaskDoingFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59"),
                            taskRepository.getTotalTaskDoneFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59"),
                            taskRepository.getTotalTaskOvertimeFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59")));
                }
            }
            return listResponses;
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "TASK STATISTIC FROM DATE TO DATE",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public TaskStatisticsResponse getTaskStatisticsWithMonthAndYear(String jwt, int month, int year) {
        try {
            return new TaskStatisticsResponse(taskRepository.getTotalTaskWithMonthAndYear(month, year),
                    taskRepository.getTotalTaskDoingWithMonthAndYear(month, year),
                    taskRepository.getTotalTaskDoneWithMonthAndYear(month, year),
                    taskRepository.getTotalTaskOvertimeWithMonthAndYear(month, year));
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "TASK STATISTIC WITH MONTH AND YEAR",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public boolean setTaskStatusIfDone(int task_id, String jwt) {
        Task updateTask = taskRepository.getTaskByTask_id(task_id);
        if (updateTask != null) {
            updateTask.setTask_status("done");
            taskRepository.save(updateTask);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "TASK",
                    "UPDATE",
                    String.valueOf(task_id),
                    new Date()));
            return true;
        }
        return false;
    }
}
