package com.example.vfarmrdbackend.repository.material;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.material.MaterialOfPhase;

public interface MaterialOfPhaseRepository extends JpaRepository<MaterialOfPhase, Integer> {

        @Query(value = "select * from materialofphase m where m.phase_id = :phase_id", nativeQuery = true)
        List<MaterialOfPhase> getAllMaterialOfOnePhase(@Param("phase_id") int phase_id);

        @Query(value = "select * from materialofphase m where m.mop_id = :mop_id", nativeQuery = true)
        MaterialOfPhase getOneMaterialOfOnePhase(@Param("mop_id") int mop_id);

        @Query(value = "select SUM(*) from materialofphase m where m.phase_id = :phase_id", nativeQuery = true)
        int getAmountOfMaterialInPhase();

        @Query(value = "select m.mop_id from materialofphase m where m.phase_id = :phase_id", nativeQuery = true)
        List<Integer> getAllMaterial_idInPhase(@Param("phase_id") int phase_id);

        @Query(value = "select distinct m.material_id from materialofphase m where m.phase_id in (select p.phase_id from phases p join formulas f where p.formula_id = f.formula_id and f.project_id = :project_id);", nativeQuery = true)
        List<String> getAllMaterial_idWithProject_id(@Param("project_id") int project_id);

        @Query(value = "SELECT count(distinct m.material_id) FROM materialofphase m;", nativeQuery = true)
        int getTotalMaterialUsed();

        @Query(value = "SELECT material_id from materialofphase m group by m.material_id order by count(m.material_id) desc limit 1;", nativeQuery = true)
        String getMostMaterial_idUsed();

        @Query(value = "SELECT count(m.material_id) from materialofphase m group by m.material_id order by count(m.material_id) desc limit 1;", nativeQuery = true)
        int getMostMaterial_idUsedTime();

        @Query(value = "SELECT material_id from materialofphase m group by m.material_id order by sum(m.material_percent) desc limit 10;", nativeQuery = true)
        List<String> getTop10MaterialMostUsedByPercent();

        @Query(value = "SELECT sum(m.material_percent) from materialofphase m group by m.material_id order by sum(m.material_percent) desc limit 10;", nativeQuery = true)
        List<Float> getTop10MaterialMostUsedTimeByPercent();

        @Query(value = "SELECT material_id from materialofphase m group by m.material_id order by sum(m.material_weight) desc limit 10;", nativeQuery = true)
        List<String> getTop10MaterialMostUsedByWeight();

        @Query(value = "SELECT sum(m.material_weight) from materialofphase m group by m.material_id order by sum(m.material_weight) desc limit 10;", nativeQuery = true)
        List<Float> getTop10MaterialMostUsedTimeByWeight();

}
