package com.example.vfarmrdbackend.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.business.model.Test;

public interface TestRepository extends JpaRepository<Test, Integer> {

    @Query(value = "select * from tests t where t.formula_id = :formula_id", nativeQuery = true)
    List<Test> getTestWithFormula_id(@Param("formula_id") int formula_id);

    @Query(value = "select * from tests t where t.test_id = :test_id", nativeQuery = true)
    Test getTestByTest_id(@Param("test_id") int test_id);
}
