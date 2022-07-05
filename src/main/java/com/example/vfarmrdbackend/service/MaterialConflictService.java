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

    public void createMaterialConflict(MaterialConflictRequest request) {
        MaterialConflict newMaterialConflict = new MaterialConflict();
        newMaterialConflict.setFirst_material_id(request.getFirst_material_id());
        newMaterialConflict.setSecond_material_id(request.getSecond_material_id());
        newMaterialConflict.setDescription(request.getDescription());
        materialConflictRepository.save(newMaterialConflict);
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
