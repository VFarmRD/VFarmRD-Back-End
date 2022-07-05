package com.example.vfarmrdbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.MaterialStandardPercent;

public interface MaterialStandardPercentRepository extends JpaRepository<MaterialStandardPercent, Integer> {
    @Query(value = "select * from materialstandardpercent m  where m.msp_id = :msp_id", nativeQuery = true)
    MaterialStandardPercent getMaterialStandardPercentById(@Param("msp_id") int msp_id);
}
