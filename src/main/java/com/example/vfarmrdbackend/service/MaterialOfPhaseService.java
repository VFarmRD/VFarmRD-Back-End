package com.example.vfarmrdbackend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.ErrorModel;
import com.example.vfarmrdbackend.model.MaterialOfPhase;
import com.example.vfarmrdbackend.payload.request.MaterialOfPhaseCreateRequest;
import com.example.vfarmrdbackend.payload.request.MaterialOfPhaseUpdateRequest;
import com.example.vfarmrdbackend.repository.MaterialOfPhaseRepository;

@Service
public class MaterialOfPhaseService {
    @Autowired
    MaterialOfPhaseRepository materialOfPhaseRepository;

    @Autowired
    ErrorService errorService;

    public List<MaterialOfPhase> getAllMaterialOfPhase(int phase_id) {
        return materialOfPhaseRepository.getAllMaterialOfOnePhase(phase_id);
    }

    public MaterialOfPhase getMaterialOfPhase(int mop_id) {
        return materialOfPhaseRepository.getOneMaterialOfOnePhase(mop_id);
    }

    public void createMaterialOfPhase(int phase_id, MaterialOfPhaseCreateRequest materialOfPhaseCreateRequest,
            String jwt) {
        MaterialOfPhase materialOfPhase = new MaterialOfPhase();
        materialOfPhase.setPhase_id(phase_id);
        materialOfPhase.setMaterial_id(materialOfPhaseCreateRequest.getMaterial_id());
        materialOfPhase.setMaterial_cost(materialOfPhaseCreateRequest.getMaterial_cost());
        materialOfPhase.setMaterial_weight(materialOfPhaseCreateRequest.getMaterial_weight());
        materialOfPhase.setMaterial_percent(materialOfPhaseCreateRequest.getMaterial_percent());
        materialOfPhase.setMaterial_description(materialOfPhaseCreateRequest.getMaterial_description());
        materialOfPhaseRepository.save(materialOfPhase);
    }

    public boolean updateMaterialOfPhase(MaterialOfPhaseUpdateRequest materialOfPhaseUpdateRequest, String jwt) {
        MaterialOfPhase materialOfPhase = materialOfPhaseRepository
                .getOneMaterialOfOnePhase(materialOfPhaseUpdateRequest.getMop_id());
        if (materialOfPhase != null) {
            materialOfPhase.setMaterial_id(materialOfPhaseUpdateRequest.getMaterial_id());
            materialOfPhase.setMaterial_cost(materialOfPhaseUpdateRequest.getMaterial_cost());
            materialOfPhase.setMaterial_weight(materialOfPhaseUpdateRequest.getMaterial_weight());
            materialOfPhase.setMaterial_percent(materialOfPhaseUpdateRequest.getMaterial_percent());
            materialOfPhase.setMaterial_description(materialOfPhaseUpdateRequest.getMaterial_description());
            materialOfPhaseRepository.save(materialOfPhase);
            return true;
        }
        return false;
    }

    public boolean deleteMaterialOfPhase(int mop_id, String jwt) {
        MaterialOfPhase materialOfPhase = materialOfPhaseRepository
                .getOneMaterialOfOnePhase(mop_id);
        if (materialOfPhase != null) {
            materialOfPhaseRepository.delete(materialOfPhase);
            return true;
        }
        return false;
    }

    public void deleteAllMaterialsOfPhaseByPhase_id(int phase_id) {
        List<MaterialOfPhase> listMOP = materialOfPhaseRepository.getAllMaterialOfOnePhase(phase_id);
        for (int i = 0; i < listMOP.size(); i++) {
            materialOfPhaseRepository.delete(listMOP.get(i));
        }
    }

    public List<String> getAllMaterial_idWithProject_id(int project_id, String jwt) {
        try {
            return materialOfPhaseRepository.getAllMaterial_idWithProject_id(project_id);
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "MATERIAL GET ALL WITH PROJECT ID",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }
}
