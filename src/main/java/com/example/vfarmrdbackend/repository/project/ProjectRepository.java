package com.example.vfarmrdbackend.repository.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.project.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
        @Query(value = "select * from projects p where p.project_id = :project_id", nativeQuery = true)
        Project getProjectByProject_id(@Param("project_id") int project_id);

        @Query(value = "select * from projects p where p.project_code = :project_code", nativeQuery = true)
        Project getProjectByProject_code(@Param("project_code") String project_code);

        @Query(value = "SELECT * FROM projects p where p.project_id in (select project_id from userinproject u where u.user_id = :user_id) and p.project_status like :project_status", nativeQuery = true)
        List<Project> getProjectByUser_id(@Param("user_id") int user_id,
                        @Param("project_status") String project_status);

        @Query(value = "select * from projects p where p.project_id in (select distinct p.project_id from projects p, formulas f where p.project_id = f.project_id and f.formula_status like :formula_status and f.created_user_id like :created_user_id) order by p.project_id", nativeQuery = true)
        List<Project> getProjectByFormula_status(@Param("formula_status") String formula_status,
                        @Param("created_user_id") String created_user_id);

        @Query(value = "select * from projects p where p.project_id in (select f.project_id from formulas f where f.formula_id in (select ph.formula_id from phases ph where ph.phase_id in (select m.phase_id from materialofphase m where m.material_id = :material_id)));", nativeQuery = true)
        List<Project> getProjectByMaterial_id(@Param("material_id") String material_id);

        @Query(value = "SELECT COUNT(*) FROM projects p;", nativeQuery = true)
        int getTotalProject();

        @Query(value = "SELECT COUNT(*) FROM projects p where p.created_time between :from_date and :to_date;", nativeQuery = true)
        int getTotalProjectFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM projects p where month(p.created_time) = :month and year(p.created_time) = :year ;", nativeQuery = true)
        int getTotalProjectWithMonthAndYear(@Param("month") int month, @Param("year") int year);

        @Query(value = "SELECT COUNT(*) FROM projects p where p.project_status = 'running';", nativeQuery = true)
        int getTotalProjectRunning();

        @Query(value = "SELECT COUNT(*) FROM projects p where (p.created_time between :from_date and :to_date) and p.project_status = 'running';", nativeQuery = true)
        int getTotalProjectRunningFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM projects p where month(p.created_time) = :month and year(p.created_time) = :year and p.project_status = 'running';", nativeQuery = true)
        int getTotalProjectRunningWithMonthAndYear(@Param("month") int month, @Param("year") int year);

        @Query(value = "SELECT COUNT(*) FROM projects p where p.project_status = 'canceled';", nativeQuery = true)
        int getTotalProjectCanceled();

        @Query(value = "SELECT COUNT(*) FROM projects p where (p.created_time between :from_date and :to_date) and p.project_status = 'canceled';", nativeQuery = true)
        int getTotalProjectCanceledFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM projects p where month(p.created_time) = :month and year(p.created_time) = :year and p.project_status = 'canceled';", nativeQuery = true)
        int getTotalProjectCanceledWithMonthAndYear(@Param("month") int month, @Param("year") int year);

        @Query(value = "SELECT * FROM projects p where p.client_id = :client_id ;", nativeQuery = true)
        List<Project> getProjectByClient_id(@Param("client_id") String client_id);

        @Query(value = "select COUNT(*) from projects p where project_id in (select distinct f.project_id from formulas f where f.formula_id in (select p.formula_id from products p));", nativeQuery = true)
        int getTotalProjectHaveProduct();

        @Query(value = "select COUNT(*) from projects p where (p.created_time between :from_date and :to_date) and project_id in (select distinct f.project_id from formulas f where f.formula_id in (select p.formula_id from products p));", nativeQuery = true)
        int getTotalProjectHaveProductFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "select COUNT(*) from projects p where month(p.created_time) = :month and year(p.created_time) = :year and project_id in (select distinct f.project_id from formulas f where f.formula_id in (select p.formula_id from products p));", nativeQuery = true)
        int getTotalProjectHaveProductWithMonthAndYear(@Param("month") int month, @Param("year") int year);
}
