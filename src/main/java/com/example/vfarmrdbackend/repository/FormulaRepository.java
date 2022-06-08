package com.example.vfarmrdbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.Formula;

public interface FormulaRepository extends JpaRepository<Formula, Integer> {

    @Query(value = "select * from formulas f where f.formula_id = :formula_id", nativeQuery = true)
    Formula getFormulaByFormula_id(@Param("formula_id") int formula_id);

    @Query(value = "select * from formulas f where f.product_id = :product_id", nativeQuery = true)
    List<Formula> getAllFormulaByProduct_id(@Param("product_id") String product_id);

    @Query(value = "SELECT f.formula_version FROM formulas f WHERE f.product_id = :product_id ORDER BY f.formula_version desc limit 1;", nativeQuery = true)
    String getLastestVersionOfProduct(@Param("product_id") String product_id);
    
}