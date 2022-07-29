package com.example.vfarmrdbackend.repository.test;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.test.TestStandard;

public interface TestStandardRepository extends JpaRepository<TestStandard, Integer> {
    @Query(value = "select * from teststandards t where t.teststandard_id = :teststandard_id", nativeQuery = true)
    TestStandard getTestStandard(@Param("teststandard_id") int teststandard_id);

    @Query(value = "select * from teststandards t where t.teststandardset_id = :teststandardset_id", nativeQuery = true)
    List<TestStandard> getTestStandardWithSet_id(@Param("teststandardset_id") int teststandardset_id);

    @Query(value = "select t.teststandard_id from teststandards t where t.teststandardset_id = :teststandardset_id", nativeQuery = true)
    List<Integer> getTestStandard_idWithSet_id(@Param("teststandardset_id") int teststandardset_id);
}
