package com.example.vfarmrdbackend.repository.material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.material.MaterialStandardPercent;

public interface MaterialStandardPercentRepository extends JpaRepository<MaterialStandardPercent, Integer> {
    @Query(value = "select * from materialstandardpercent m  where m.msp_id = :msp_id", nativeQuery = true)
    MaterialStandardPercent getMaterialStandardPercentById(@Param("msp_id") int msp_id);

    @Query(value = "select * from materialstandardpercent m  where m.material_id = :material_id", nativeQuery = true)
    MaterialStandardPercent getMaterialStandardPercentByMaterial_id(@Param("material_id") String material_id);
}
