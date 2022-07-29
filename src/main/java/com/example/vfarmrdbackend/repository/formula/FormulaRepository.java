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

}
