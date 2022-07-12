package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.MaterialStandardPercent;
import com.example.vfarmrdbackend.payload.MaterialStandardPercentRequest;
import com.example.vfarmrdbackend.repository.MaterialStandardPercentRepository;

@Service
public class MaterialStandardPercentService {
    @Autowired
    MaterialStandardPercentRepository materialStandardPercentRepository;

    public List<MaterialStandardPercent> getAllMaterialStandardPercent() {
        return materialStandardPercentRepository.findAll();
    }

    public MaterialStandardPercent getMaterialStandardPercent(int msp_id) {
        return materialStandardPercentRepository.getMaterialStandardPercentById(msp_id);
    }

    public MaterialStandardPercent getMaterialStandardPercentByMaterial_id(String material_id) {
        return materialStandardPercentRepository.getMaterialStandardPercentByMaterial_id(material_id);
    }

    public void createMaterialStandardPercent(MaterialStandardPercentRequest request, String jwt) {
        MaterialStandardPercent newMaterialStandardPercent = new MaterialStandardPercent();
        newMaterialStandardPercent.setMaterial_id(request.getMaterial_id());
        newMaterialStandardPercent.setMax_percent(request.getMax_percent());
        materialStandardPercentRepository.save(newMaterialStandardPercent);
    }

    public boolean updateMaterialStandardPercent(int msp_id, MaterialStandardPercentRequest request, String jwt) {
        MaterialStandardPercent updateMaterialStandardPercent = materialStandardPercentRepository
                .getMaterialStandardPercentById(msp_id);
        if (updateMaterialStandardPercent != null) {
            updateMaterialStandardPercent.setMaterial_id(request.getMaterial_id());
            updateMaterialStandardPercent.setMax_percent(request.getMax_percent());
            materialStandardPercentRepository.save(updateMaterialStandardPercent);
            return true;
        }
        return false;
    }

    public boolean deleteMaterialStandardPercent(int msp_id, String jwt) {
        MaterialStandardPercent deleteMaterialStandardPercent = materialStandardPercentRepository
                .getMaterialStandardPercentById(msp_id);
        if (deleteMaterialStandardPercent != null) {
            materialStandardPercentRepository.delete(deleteMaterialStandardPercent);
            return true;
        }
        return false;
    }
}
