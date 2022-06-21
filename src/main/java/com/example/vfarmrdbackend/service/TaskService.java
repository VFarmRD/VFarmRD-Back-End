package com.example.vfarmrdbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    Date date;

    public List<TaskGetResponse> getAllTasks(int user_id) {
        List<Task> listTasks = new ArrayList<>();
        List<TaskGetResponse> listTasksResponse = new ArrayList<>();
        User requestUser = userRepository.getUserByUser_id(user_id);
        if (requestUser.getRole_name().equals("staff")) {
            listTasks = taskRepository.getAllTasksWithUser_idAndRoleStaff(user_id);
        } else {
            listTasks = taskRepository.findAll();
        }
        for (int i = 0; i < listTasks.size(); i++) {
            date = new Date();
            Task task = listTasks.get(i);
            TaskGetResponse newTaskInfo = new TaskGetResponse();
            User user = userRepository.getUserByUser_id(task.getUser_id());
            newTaskInfo.setTask_id(task.getTask_id());
            newTaskInfo.setTask_name(task.getTask_name());
            newTaskInfo.setUser_id(task.getUser_id());
            newTaskInfo.setUser_name(user.getUser_name());
            newTaskInfo.setUser_role(user.getRole_name());
            newTaskInfo.setProduct_id(task.getProduct_id());
            newTaskInfo.setCreated_date(task.getCreated_date());
            newTaskInfo.setEstimated_date(task.getEstimated_date());
            if (date.after(task.getEstimated_date()) && !task.getTask_status().equals("done")) {
                newTaskInfo.setTask_status("overtime");
            } else {
                newTaskInfo.setTask_status(task.getTask_status());
            }
            newTaskInfo.setDescription(task.getDescription());
        }
        return listTasksResponse;
    }

    public List<Task> getAllTasksWithUser_id(int user_id) {
        return taskRepository.getAllTasksWithUser_id(user_id);
    }

    public Task getTaskByTask_id(int task_id) {
        return taskRepository.getTaskByTask_id(task_id);
    }

    public void createTask(TaskCreateRequest taskCreateRequest) {
        date = new Date();
        Task newTask = new Task();
        newTask.setTask_name(taskCreateRequest.getTask_name());
        newTask.setUser_id(taskCreateRequest.getUser_id());
        newTask.setProduct_id(taskCreateRequest.getProduct_id());
        newTask.setCreated_date(date);
        newTask.setEstimated_date(taskCreateRequest.getEstimated_date());
        newTask.setDescription(taskCreateRequest.getDescription());
        newTask.setTask_status("doing");
        taskRepository.save(newTask);
    }

    public boolean updateTask(int task_id, TaskUpdateRequest taskUpdateRequest) {
        Task updateTask = taskRepository.getTaskByTask_id(task_id);
        if (updateTask != null) {
            updateTask.setTask_name(taskUpdateRequest.getTask_name());
            updateTask.setUser_id(taskUpdateRequest.getUser_id());
            updateTask.setEstimated_date(taskUpdateRequest.getEstimated_date());
            updateTask.setDescription(taskUpdateRequest.getDescription());
            updateTask.setTask_status(taskUpdateRequest.getTask_status());
            taskRepository.save(updateTask);
            return true;
        }
        return false;
    }

    public boolean deleteTask(int task_id) {
        Task deleteTask = taskRepository.getTaskByTask_id(task_id);
        if (deleteTask != null) {
            deleteTask.setTask_status("deleted");
            taskRepository.save(deleteTask);
            return true;
        }
        return false;
    }
}
