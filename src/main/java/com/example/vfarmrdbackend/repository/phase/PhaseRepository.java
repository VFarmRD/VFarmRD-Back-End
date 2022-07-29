package com.example.vfarmrdbackend.repository.phase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.phase.Phase;

public interface PhaseRepository extends JpaRepository<Phase, Integer> {

    @Query(value = "select * from phases p where p.formula_id = :formula_id", nativeQuery = true)
    List<Phase> getAllPhaseByFormula_id(@Param("formula_id") int formula_id);

    @Query(value = "select * from phases p where p.phase_id = :phase_id", nativeQuery = true)
    Phase getPhaseByPhase_id(@Param("phase_id") int phase_id);

    @Query(value = "SELECT p.phase_id FROM phases p order by p.phase_id desc limit 1;", nativeQuery = true)
    int getLatestPhase_id();

    @Query(value = "select p.phase_id from phases p where p.formula_id = :formula_id", nativeQuery = true)
    List<Integer> getAllPhase_idOfFormula(@Param("formula_id") int formula_id);
}
