package com.example.vfarmrdbackend.repositories;

import com.example.vfarmrdbackend.models.MaterialOfPhase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MaterialOfPhaseRepository extends JpaRepository<MaterialOfPhase,Integer> {

    @Query(value = "select * from phases p where p.phase_id = :phase_id", nativeQuery = true)
    MaterialOfPhase getAllMaterialOfOnePhase(@Param("phase_id") int phase_id);
    
}
