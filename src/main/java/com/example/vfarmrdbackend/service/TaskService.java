package com.example.vfarmrdbackend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Task;
import com.example.vfarmrdbackend.payload.TaskCreateRequest;
import com.example.vfarmrdbackend.payload.TaskUpdateRequest;
import com.example.vfarmrdbackend.repository.TaskRepository;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    Date date;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
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
        newTask.setTask_status(true);
        taskRepository.save(newTask);
    }

    public boolean updateTask(int task_id, TaskUpdateRequest taskUpdateRequest) {
        Task updateTask = taskRepository.getTaskByTask_id(task_id);
        if (updateTask != null) {
            updateTask.setTask_name(taskUpdateRequest.getTask_name());
            updateTask.setUser_id(taskUpdateRequest.getUser_id());
            updateTask.setEstimated_date(taskUpdateRequest.getEstimated_date());
            updateTask.setDescription(taskUpdateRequest.getDescription());
            updateTask.setTask_status(taskUpdateRequest.isTask_status());
            taskRepository.save(updateTask);
            return true;
        }
        return false;
    }

    public boolean deleteTask(int task_id) {
        Task deleteTask = taskRepository.getTaskByTask_id(task_id);
        if (deleteTask != null) {
            deleteTask.setTask_status(false);
            taskRepository.save(deleteTask);
            return true;
        }
        return false;
    }
}
