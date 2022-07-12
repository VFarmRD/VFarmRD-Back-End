package com.example.vfarmrdbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Log;
import com.example.vfarmrdbackend.model.Task;
import com.example.vfarmrdbackend.model.User;
import com.example.vfarmrdbackend.payload.TaskCreateRequest;
import com.example.vfarmrdbackend.payload.TaskGetResponse;
import com.example.vfarmrdbackend.payload.TaskUpdateRequest;
import com.example.vfarmrdbackend.repository.TaskRepository;
import com.example.vfarmrdbackend.repository.UserRepository;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    LogService logService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void setTask_statusIfOvertime() {
        Date date = new Date();
        List<Task> listTasks = taskRepository.findAll();
        for (int i = 0; i < listTasks.size(); i++) {
            Task task = listTasks.get(i);
            if (date.after(task.getEstimated_date()) &&
                    !task.getTask_status().equals("done")) {
                task.setTask_status("overtime");
            }
            taskRepository.save(task);
        }
    }

    public List<TaskGetResponse> getAllTasks(int user_id) {
        List<Task> listTasks = new ArrayList<>();
        List<TaskGetResponse> listTasksResponse = new ArrayList<>();
        User requestUser = userRepository.getUserByUser_id(user_id);
        if (requestUser.getRole_name().equals("staff")) {
            listTasks = taskRepository.getAllTasksWithUser_id(user_id);
        } else {
            listTasks = taskRepository.findAll();
        }
        for (int i = 0; i < listTasks.size(); i++) {
            Task task = listTasks.get(i);
            TaskGetResponse newTaskInfo = new TaskGetResponse();
            User user = userRepository.getUserByUser_id(task.getUser_id());
            newTaskInfo.setTask_id(task.getTask_id());
            newTaskInfo.setTask_name(task.getTask_name());
            newTaskInfo.setUser_id(task.getUser_id());
            newTaskInfo.setUser_name(user.getUser_name());
            newTaskInfo.setUser_role(user.getRole_name());
            newTaskInfo.setProject_id(task.getProject_id());
            newTaskInfo.setCreated_date(task.getCreated_date());
            newTaskInfo.setStart_date(task.getStart_date());
            newTaskInfo.setEstimated_date(task.getEstimated_date());
            newTaskInfo.setTask_status(task.getTask_status());
            newTaskInfo.setDescription(task.getDescription());
            listTasksResponse.add(newTaskInfo);
        }
        return listTasksResponse;
    }

    public List<Task> getAllTasksWithUser_id(int user_id) {
        return taskRepository.getAllTasksWithUser_id(user_id);
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
        logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                "TASK",
                "CREATE",
                String.valueOf(taskRepository.getNewestTaskByProject_id(newTask.getProject_id())),
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
}
