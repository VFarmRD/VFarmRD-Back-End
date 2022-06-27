package com.example.vfarmrdbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.TestStandard;

public interface TestStandardRepository extends JpaRepository<TestStandard, Integer> {
    @Query(value = "select * from teststandards t where m.teststandard_id = :teststandard_id", nativeQuery = true)
    TestStandard getTestStandard(@Param("teststandard_id") int teststandard_id);
}
