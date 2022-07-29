package com.example.vfarmrdbackend.repository.tool;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.tool.ToolInPhase;

public interface ToolInPhaseRepository extends JpaRepository<ToolInPhase, Integer> {
    @Query(value = "select * from toolinphases t where t.toolinphase_id = :toolinphase_id", nativeQuery = true)
    ToolInPhase getToolInPhaseByToolinphase_id(@Param("toolinphase_id") int toolinphase_id);

    @Query(value = "select t.toolinphase_id from toolinphases t where t.phase_id = :phase_id", nativeQuery = true)
    List<Integer> getToolInPhase_idByPhase_id(@Param("phase_id") int phase_id);

    @Query(value = "select * from toolinphases t where t.phase_id = :phase_id", nativeQuery = true)
    List<ToolInPhase> getToolInPhaseByPhase_id(@Param("phase_id") int phase_id);
}
