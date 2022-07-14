package com.example.vfarmrdbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.ToolInPhase;

public interface ToolInPhaseRepository extends JpaRepository<ToolInPhase, Integer> {
    @Query(value = "select * from toolinphases t where t.toolinphase_id = :toolinphase_id", nativeQuery = true)
    ToolInPhase getToolInPhaseByToolinphase_id(@Param("toolinphase_id") int toolinphase_id);
}
