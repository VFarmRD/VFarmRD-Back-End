package com.example.vfarmrdbackend.repository.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.test.TestStandardSet;

public interface TestStandardSetRepository extends JpaRepository<TestStandardSet, Integer> {
    @Query(value = "select t.teststandardset_id from teststandardsets t where t.teststandardset_name = :teststandardset_name", nativeQuery = true)
    int getTestStandard_idByName(@Param("teststandardset_name") String teststandardset_name);

    @Query(value = "select * from teststandardsets t where t.teststandardset_id = :teststandardset_id", nativeQuery = true)
    TestStandardSet getTestStandardById(@Param("teststandardset_id") int teststandardset_id);
}
