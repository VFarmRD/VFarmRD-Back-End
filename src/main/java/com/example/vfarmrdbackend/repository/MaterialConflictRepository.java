package com.example.vfarmrdbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.MaterialConflict;

public interface MaterialConflictRepository extends JpaRepository<MaterialConflict, Integer> {
    @Query(value = "select * from materialconflicts m  where m.materialconflict_id = :materialconflict_id", nativeQuery = true)
    MaterialConflict getMaterialConflictById(@Param("materialconflict_id") int materialconflict_id);

    @Query(value = "select * from materialconflicts m  where m.first_material_id = :first_material_id", nativeQuery = true)
    MaterialConflict getMaterialConflictByFirstMaterialId(@Param("first_material_id") int first_material_id);
}
