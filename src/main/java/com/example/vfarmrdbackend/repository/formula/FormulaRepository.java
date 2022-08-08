package com.example.vfarmrdbackend.repository.formula;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.formula.Formula;

public interface FormulaRepository extends JpaRepository<Formula, Integer> {

        @Query(value = "select * from formulas f where f.formula_id = :formula_id", nativeQuery = true)
        Formula getFormulaByFormula_id(@Param("formula_id") int formula_id);

        @Query(value = "select * from formulas f where f.project_id = :project_id and f.formula_status like :formula_status", nativeQuery = true)
        List<Formula> getAllFormulaByProject_idAndStatus(@Param("project_id") int project_id,
                        @Param("formula_status") String formula_status);

        @Query(value = "select * from formulas f where f.created_user_id = :created_user_id and f.formula_status like :formula_status", nativeQuery = true)
        List<Formula> getAllFormulaByUser_idAndFormula_status(@Param("created_user_id") int created_user_id,
                        @Param("formula_status") String formula_status);

        @Query(value = "select * from formulas f where f.project_id = :project_id", nativeQuery = true)
        List<Formula> getAllFormulaByProject_id(@Param("project_id") int project_id);

        @Query(value = "SELECT count(*) FROM formulas f WHERE f.project_id = :project_id", nativeQuery = true)
        int getTotalFormulaOfProduct(@Param("project_id") int project_id);

        @Query(value = "SELECT f.formula_id FROM formulas f WHERE f.project_id = :project_id order by f.formula_id desc limit 1", nativeQuery = true)
        int getLatestFormula_idOfProject(@Param("project_id") int project_id);

        @Query(value = "SELECT count(*) FROM formulas f WHERE f.project_id = :project_id and f.formula_pre_version = :formula_pre_version", nativeQuery = true)
        int totalFormulaHaveMatchPreVersion(@Param("project_id") int project_id,
                        @Param("formula_pre_version") String formula_pre_version);

        @Query(value = "select * from formulas f where f.project_id = :project_id and f.formula_version = :formula_version", nativeQuery = true)
        int getFormula_idByProject_idAndVersion(@Param("project_id") int project_id,
                        @Param("formula_version") String formula_version);

        @Query(value = "SELECT COUNT(*) FROM formulas f;", nativeQuery = true)
        int getTotalFormula();

        @Query(value = "SELECT COUNT(*) FROM formulas f where (f.created_time between :from_date and :to_date);", nativeQuery = true)
        int getTotalFormulaFromDateToDate(@Param("from_date") String from_date, @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM formulas f where month(f.created_time) = :month and year(f.created_time) = :year ;", nativeQuery = true)
        int getTotalFormulaWithMonthAndYear(@Param("month") int month, @Param("year") int year);

        @Query(value = "SELECT COUNT(*) FROM formulas f where f.formula_status = 'pending';", nativeQuery = true)
        int getTotalFormulaPending();

        @Query(value = "SELECT COUNT(*) FROM formulas f where (f.created_time between :from_date and :to_date) and f.formula_status = 'pending';", nativeQuery = true)
        int getTotalFormulaPendingFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM formulas f where month(f.created_time) = :month and year(f.created_time) = :year and f.formula_status = 'pending';", nativeQuery = true)
        int getTotalFormulaPendingWithMonthAndYear(@Param("month") int month, @Param("year") int year);

        @Query(value = "SELECT COUNT(*) FROM formulas f where f.formula_status = 'on process';", nativeQuery = true)
        int getTotalFormulaOnProcess();

        @Query(value = "SELECT COUNT(*) FROM formulas f where (f.created_time between :from_date and :to_date) and f.formula_status = 'on process';", nativeQuery = true)
        int getTotalFormulaOnProcessFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM formulas f where month(f.created_time) = :month and year(f.created_time) = :year and f.formula_status = 'on process';", nativeQuery = true)
        int getTotalFormulaOnProcessWithMonthAndYear(@Param("month") int month, @Param("year") int year);

        @Query(value = "SELECT COUNT(*) FROM formulas f where f.formula_status = 'approved';", nativeQuery = true)
        int getTotalFormulaApproved();

        @Query(value = "SELECT COUNT(*) FROM formulas f where (f.created_time between :from_date and :to_date) and f.formula_status = 'approved';", nativeQuery = true)
        int getTotalFormulaApprovedFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM formulas f where month(f.created_time) = :month and year(f.created_time) = :year and f.formula_status = 'approved';", nativeQuery = true)
        int getTotalFormulaApprovedWithMonthAndYear(@Param("month") int month, @Param("year") int year);

}
