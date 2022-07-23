package com.example.vfarmrdbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.MaterialOfPhase;

public interface MaterialOfPhaseRepository extends JpaRepository<MaterialOfPhase, Integer> {

    @Query(value = "select * from materialofphase m where m.phase_id = :phase_id", nativeQuery = true)
    List<MaterialOfPhase> getAllMaterialOfOnePhase(@Param("phase_id") int phase_id);

    @Query(value = "select * from materialofphase m where m.mop_id = :mop_id", nativeQuery = true)
    MaterialOfPhase getOneMaterialOfOnePhase(@Param("mop_id") int mop_id);

    @Query(value = "select SUM(*) from materialofphase m where m.phase_id = :phase_id", nativeQuery = true)
    int getAmountOfMaterialInPhase();

    @Query(value = "select m.mop_id from materialofphase m where m.phase_id = :phase_id", nativeQuery = true)
    List<Integer> getAllMaterial_idInPhase(@Param("phase_id") int phase_id);

    @Query(value = "select distinct m.material_id from materialofphase m where m.phase_id in (select p.phase_id from phases p join formulas f where p.formula_id = f.formula_id and f.project_id = :project_id); ", nativeQuery = true)
    List<String> getAllMaterial_idWithProject_id(@Param("project_id") int project_id);
}
