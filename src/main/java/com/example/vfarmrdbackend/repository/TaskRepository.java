package com.example.vfarmrdbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query(value = "select * from tasks t where t.task_id = :task_id", nativeQuery = true)
    Task getTaskByTask_id(@Param("task_id") int task_id);

    @Query(value = "select * from tasks t where t.user_id = :user_id", nativeQuery = true)
    List<Task> getAllTasksWithUser_id(@Param("user_id") int user_id);
}
