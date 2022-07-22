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

    @Query(value = "select * from projects p where p.assigned_user_id = :assigned_user_id and p.project_status like :project_status", nativeQuery = true)
    List<Project> getProjectByAssigned_user_id(@Param("assigned_user_id") int assigned_user_id,
            @Param("project_status") String project_status);

    @Query(value = "select * from projects p where p.project_id in (select distinct p.project_id from projects p, formulas f where p.project_id = f.project_id and f.formula_status like :formula_status and f.created_user_id like :created_user_id) order by p.project_id", nativeQuery = true)
    List<Project> getProjectByFormula_status(@Param("formula_status") String formula_status,
    @Param("created_user_id") String created_user_id);
}
