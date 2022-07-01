package com.example.vfarmrdbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.Formula;

public interface FormulaRepository extends JpaRepository<Formula, Integer> {

    @Query(value = "select * from formulas f where f.formula_id = :formula_id", nativeQuery = true)
    Formula getFormulaByFormula_id(@Param("formula_id") int formula_id);

    @Query(value = "select * from formulas f where f.project_id = :project_id and f.formula_status like :formula_status", nativeQuery = true)
    List<Formula> getAllFormulaByProject_idAndStatus(@Param("project_id") String project_id,
            @Param("formula_status") String formula_status);

    @Query(value = "select * from formulas f where f.project_id = :project_id", nativeQuery = true)
    List<Formula> getAllFormulaByProject_id(@Param("project_id") String project_id);

    @Query(value = "SELECT count(*) FROM formulas f WHERE f.project_id = :project_id", nativeQuery = true)
    int getTotalFormulaOfProduct(@Param("project_id") String project_id);

    @Query(value = "SELECT f.formula_id FROM formulas f order by f.formula_id desc limit 1", nativeQuery = true)
    int getLatestFormula_id();

    @Query(value = "SELECT count(*) FROM formulas f WHERE f.project_id = :project_id and f.formula_pre_version = :formula_pre_version", nativeQuery = true)
    int totalFormulaHaveMatchPreVersion(@Param("project_id") String project_id,
            @Param("formula_pre_version") String formula_pre_version);

}
