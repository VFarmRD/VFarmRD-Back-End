package com.example.vfarmrdbackend.repository.material;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.material.MaterialConflict;

public interface MaterialConflictRepository extends JpaRepository<MaterialConflict, Integer> {
    @Query(value = "select * from materialconflicts m  where m.materialconflict_id = :materialconflict_id", nativeQuery = true)
    MaterialConflict getMaterialConflictById(@Param("materialconflict_id") int materialconflict_id);

    @Query(value = "select * from materialconflicts m  where m.first_material_id = :first_material_id", nativeQuery = true)
    List<MaterialConflict> getMaterialConflictByFirstMaterialId(@Param("first_material_id") String first_material_id);

    @Query(value = "select m.materialconflict_id from materialconflicts m  where m.first_material_id = :material_id or m.second_material_id = :material_id", nativeQuery = true)
    List<Integer> getMaterialConflictIdBMaterial_id(@Param("material_id") String material_id);

    @Query(value = "select * from materialconflicts m  where m.first_material_id = :material_id or m.second_material_id = :material_id", nativeQuery = true)
    List<MaterialConflict> getMaterialConflictByMaterial_id(@Param("material_id") String material_id);

    @Query(value = "select * from materialconflicts m  where m.first_material_id = :first_material_id and m.second_material_id = :second_material_id", nativeQuery = true)
    MaterialConflict getMaterialConflictByTwoMaterial_id(@Param("first_material_id") String first_material_id,@Param("second_material_id") String second_material_id);
}
