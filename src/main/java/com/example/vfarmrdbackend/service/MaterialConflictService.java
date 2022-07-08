package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.MaterialConflict;
import com.example.vfarmrdbackend.payload.MaterialConflictRequest;
import com.example.vfarmrdbackend.repository.MaterialConflictRepository;

@Service
public class MaterialConflictService {
    @Autowired
    MaterialConflictRepository materialConflictRepository;

    public List<MaterialConflict> getAllMaterialConflict() {
        return materialConflictRepository.findAll();
    }

    public MaterialConflict getMaterialConflictById(int materialconflict_id) {
        return materialConflictRepository.getMaterialConflictById(materialconflict_id);
    }

    public MaterialConflict getMaterialConflictByFirstMaterialId(String first_material_id) {
        return materialConflictRepository.getMaterialConflictByFirstMaterialId(first_material_id);
    }

    public void createMaterialConflict(MaterialConflictRequest request) {
        MaterialConflict firstNewMaterialConflict = new MaterialConflict();
        firstNewMaterialConflict.setFirst_material_id(request.getFirst_material_id());
        firstNewMaterialConflict.setSecond_material_id(request.getSecond_material_id());
        firstNewMaterialConflict.setDescription(request.getDescription());
        materialConflictRepository.save(firstNewMaterialConflict);
        MaterialConflict secondNewMaterialConflict = new MaterialConflict();
        secondNewMaterialConflict.setFirst_material_id(request.getSecond_material_id());
        secondNewMaterialConflict.setSecond_material_id(request.getFirst_material_id());
        secondNewMaterialConflict.setDescription(request.getDescription());
        materialConflictRepository.save(secondNewMaterialConflict);
    }

    public boolean updateMaterialConflict(int materialconflict_id, MaterialConflictRequest request) {
        MaterialConflict updateConflict = materialConflictRepository.getMaterialConflictById(materialconflict_id);
        if (updateConflict != null) {
            updateConflict.setFirst_material_id(request.getFirst_material_id());
            updateConflict.setSecond_material_id(request.getSecond_material_id());
            updateConflict.setDescription(request.getDescription());
            materialConflictRepository.save(updateConflict);
            return true;
        }
        return false;
    }

    public boolean deleteMaterialConflict(int materialconflict_id) {
        MaterialConflict deleteConflict = materialConflictRepository.getMaterialConflictById(materialconflict_id);
        if (deleteConflict != null) {
            materialConflictRepository.delete(deleteConflict);
            return true;
        }
        return false;
    }
}
