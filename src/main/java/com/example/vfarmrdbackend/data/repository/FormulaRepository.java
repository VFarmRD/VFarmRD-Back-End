package com.example.vfarmrdbackend.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.business.model.Formula;

public interface FormulaRepository extends JpaRepository<Formula, Integer> {

    @Query(value = "select * from formulas f where f.formula_id = :formula_id", nativeQuery = true)
    Formula getFormulaByFormula_id(@Param("formula_id") int formula_id);

    @Query(value = "select * from formulas f where f.product_id = :product_id", nativeQuery = true)
    List<Formula> getAllFormulaByProduct_id(@Param("product_id") int product_id);
}
