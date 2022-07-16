package com.example.vfarmrdbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query(value = "select * from projects p where p.project_id = :project_id", nativeQuery = true)
    Project getProjectByProject_id(@Param("project_id") int project_id);

    @Query(value = "select * from projects p where p.project_code = :project_code", nativeQuery = true)
    Project getProjectByProject_code(@Param("project_code") String project_code);

    @Query(value = "select * from projects p where p.assigned_user_id = :assigned_user_id", nativeQuery = true)
    List<Project> getProjectByAssigned_user_id(@Param("assigned_user_id") int assigned_user_id);

    @Query(value = "select * from projects p where p.project_status LIKE :project_status", nativeQuery = true)
    List<Project> getProjectByStatus(@Param("project_status") String project_status);
}
