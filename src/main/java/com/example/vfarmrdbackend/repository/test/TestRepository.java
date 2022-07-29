package com.example.vfarmrdbackend.repository.test;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.test.Test;

public interface TestRepository extends JpaRepository<Test, Integer> {

    @Query(value = "select * from tests t where t.formula_id = :formula_id", nativeQuery = true)
    List<Test> getTestWithFormula_id(@Param("formula_id") int formula_id);

    @Query(value = "select * from tests t where t.test_id = :test_id", nativeQuery = true)
    Test getTestByTest_id(@Param("test_id") int test_id);

    @Query(value = "select count(*) from tests t where t.formula_id = :formula_id and t.test_result = true", nativeQuery = true)
    int getAllPassTestWithFormula_id(@Param("formula_id") int formula_id);

    @Query(value = "select count(*) from tests t where t.formula_id = :formula_id and t.test_result = false", nativeQuery = true)
    int getAllNotPassTestWithFormula_id(@Param("formula_id") int formula_id);

    @Query(value = "select t.test_id from tests t where t.formula_id = :formula_id", nativeQuery = true)
    List<Integer> getTest_idWithFormula_id(@Param("formula_id") int formula_id);
}
