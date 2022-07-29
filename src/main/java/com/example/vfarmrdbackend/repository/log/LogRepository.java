package com.example.vfarmrdbackend.repository.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.log.Log;

public interface LogRepository extends JpaRepository<Log, Integer> {
    @Query(value = "select * from logs l where l.log_id = :log_id", nativeQuery = true)
    Log getLogById(@Param("log_id") int log_id);
}
