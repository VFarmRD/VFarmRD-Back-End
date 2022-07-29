package com.example.vfarmrdbackend.service.material;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.log.Log;
import com.example.vfarmrdbackend.model.material.MaterialStandardPercent;
import com.example.vfarmrdbackend.payload.material.MaterialStandardPercentRequest;
import com.example.vfarmrdbackend.repository.material.MaterialStandardPercentRepository;
import com.example.vfarmrdbackend.service.log.LogService;
import com.example.vfarmrdbackend.service.others.JwtService;

@Service
public class MaterialStandardPercentService {
    @Autowired
    MaterialStandardPercentRepository materialStandardPercentRepository;

    @Autowired
    LogService logService;

    public List<MaterialStandardPercent> getAllMaterialStandardPercent() {
        return materialStandardPercentRepository.findAll();
    }

    public MaterialStandardPercent getMaterialStandardPercent(int msp_id) {
        return materialStandardPercentRepository.getMaterialStandardPercentById(msp_id);
    }

    public MaterialStandardPercent getMaterialStandardPercentByMaterial_id(String material_id) {
        return materialStandardPercentRepository.getMaterialStandardPercentByMaterial_id(material_id);
    }

    public boolean createMaterialStandardPercent(MaterialStandardPercentRequest request, String jwt) {
        if (materialStandardPercentRepository
                .getMaterialStandardPercentByMaterial_id(request.getMaterial_id()) == null) {
            MaterialStandardPercent newMaterialStandardPercent = new MaterialStandardPercent();
            newMaterialStandardPercent.setMaterial_id(request.getMaterial_id());
            newMaterialStandardPercent.setMax_percent(request.getMax_percent());
            materialStandardPercentRepository.save(newMaterialStandardPercent);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "MATERIAL STANDARD PERCENT",
                    "CREATE",
                    String.valueOf(materialStandardPercentRepository
                            .getMaterialStandardPercentByMaterial_id(request.getMaterial_id())),
                    new Date()));
            return true;
        }
        return false;
    }

    public boolean updateMaterialStandardPercent(int msp_id, MaterialStandardPercentRequest request, String jwt) {
        MaterialStandardPercent updateMaterialStandardPercent = materialStandardPercentRepository
                .getMaterialStandardPercentById(msp_id);
        if (updateMaterialStandardPercent != null) {
            updateMaterialStandardPercent.setMaterial_id(request.getMaterial_id());
            updateMaterialStandardPercent.setMax_percent(request.getMax_percent());
            materialStandardPercentRepository.save(updateMaterialStandardPercent);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "MATERIAL STANDARD PERCENT",
                    "UPDATE",
                    String.valueOf(msp_id),
                    new Date()));
            return true;
        }
        return false;
    }

    public boolean deleteMaterialStandardPercent(int msp_id, String jwt) {
        MaterialStandardPercent deleteMaterialStandardPercent = materialStandardPercentRepository
                .getMaterialStandardPercentById(msp_id);
        if (deleteMaterialStandardPercent != null) {
            materialStandardPercentRepository.delete(deleteMaterialStandardPercent);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "MATERIAL STANDARD PERCENT",
                    "UPDATE",
                    String.valueOf(msp_id),
                    new Date()));
            return true;
        }
        return false;
    }
}
