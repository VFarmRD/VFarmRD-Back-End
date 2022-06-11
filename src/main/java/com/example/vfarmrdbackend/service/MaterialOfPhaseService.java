package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.MaterialOfPhase;
import com.example.vfarmrdbackend.payload.MaterialOfPhaseCreateRequest;
import com.example.vfarmrdbackend.payload.MaterialOfPhaseUpdateRequest;
import com.example.vfarmrdbackend.repository.MaterialOfPhaseRepository;

@Service
public class MaterialOfPhaseService {
    @Autowired
    private MaterialOfPhaseRepository materialOfPhaseRepository;

    public List<MaterialOfPhase> getAllMaterialOfPhase(int phase_id) {
        return materialOfPhaseRepository.getAllMaterialOfOnePhase(phase_id);
    }

    public MaterialOfPhase getMaterialOfPhase(int mop_id) {
        return materialOfPhaseRepository.getOneMaterialOfOnePhase(mop_id);
    }

    public void createMaterialOfPhase(int phase_id, MaterialOfPhaseCreateRequest materialOfPhaseCreateRequest) {
        MaterialOfPhase _materialOfPhase = new MaterialOfPhase();
        _materialOfPhase.setPhase_id(phase_id);
        _materialOfPhase.setMaterial_id(materialOfPhaseCreateRequest.getMaterial_id());
        _materialOfPhase.setMaterial_cost(materialOfPhaseCreateRequest.getMaterial_cost());
        _materialOfPhase.setMaterial_weight(materialOfPhaseCreateRequest.getMaterial_weight());
        _materialOfPhase.setMaterial_percent(materialOfPhaseCreateRequest.getMaterial_percent());
        materialOfPhaseRepository.save(_materialOfPhase);
    }

    public boolean updateMaterialOfPhase(MaterialOfPhaseUpdateRequest materialOfPhaseUpdateRequest) {
        MaterialOfPhase _materialOfPhase = materialOfPhaseRepository
                .getOneMaterialOfOnePhase(materialOfPhaseUpdateRequest.getMop_id());
        if (_materialOfPhase != null) {
            _materialOfPhase.setMaterial_id(materialOfPhaseUpdateRequest.getMaterial_id());
            _materialOfPhase.setMaterial_cost(materialOfPhaseUpdateRequest.getMaterial_cost());
            _materialOfPhase.setMaterial_weight(materialOfPhaseUpdateRequest.getMaterial_weight());
            _materialOfPhase.setMaterial_percent(materialOfPhaseUpdateRequest.getMaterial_percent());
            materialOfPhaseRepository.save(_materialOfPhase);
            return true;
        }
        return false;
    }

    public boolean deleteMaterialOfPhase(int mop_id) {
        MaterialOfPhase _materialOfPhase = materialOfPhaseRepository
                .getOneMaterialOfOnePhase(mop_id);
        if (_materialOfPhase != null) {
            materialOfPhaseRepository.delete(_materialOfPhase);
            return true;
        }
        return false;
    }
}
