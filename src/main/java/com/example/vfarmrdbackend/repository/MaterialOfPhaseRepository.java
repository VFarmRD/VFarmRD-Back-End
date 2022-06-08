package com.example.vfarmrdbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.MaterialOfPhase;

public interface MaterialOfPhaseRepository extends JpaRepository<MaterialOfPhase,Integer> {

    @Query(value = "select * from phases p where p.phase_id = :phase_id", nativeQuery = true)
    List<MaterialOfPhase> getAllMaterialOfOnePhase(@Param("phase_id") int phase_id);

    @Query(value = "select * from phases p where p.mop_id = :mop_id", nativeQuery = true)
    MaterialOfPhase getOneMaterialOfOnePhase(@Param("mop_id") int mop_id);
    
}
