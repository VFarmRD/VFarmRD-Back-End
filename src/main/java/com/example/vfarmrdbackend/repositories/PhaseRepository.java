package com.example.vfarmrdbackend.repositories;

import java.util.List;

import com.example.vfarmrdbackend.models.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhaseRepository extends JpaRepository<Phase, Integer> {

    @Query(value = "select * from phases p where p.formula_id = :formula_id", nativeQuery = true)
    List<Phase> getAllPhaseByFormula_id(@Param("formula_id") int formula_id);

    @Query(value = "select * from phases p where p.phase_id = :phase_id", nativeQuery = true)
    Phase getPhaseByPhase_id(@Param("phase_id") int phase_id);
}
