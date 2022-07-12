package com.example.vfarmrdbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query(value = "select * from projects p where p.project_id = :project_id", nativeQuery = true)
    Project getProjectByProject_id(@Param("project_id") int project_id);

    @Query(value = "select * from projects p where p.project_code = :project_code", nativeQuery = true)
    Project getProjectByProject_code(@Param("project_code") String project_code);
}
