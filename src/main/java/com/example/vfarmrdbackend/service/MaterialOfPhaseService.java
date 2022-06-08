package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.MaterialOfPhase;
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

    public void createMaterialOfPhase(MaterialOfPhase materialOfPhase) {
        MaterialOfPhase _materialOfPhase = new MaterialOfPhase();
        _materialOfPhase.setPhase_id(materialOfPhase.getPhase_id());
        _materialOfPhase.setMaterial_id(materialOfPhase.getMaterial_id());
        _materialOfPhase.setMaterial_percent(materialOfPhase.getMaterial_percent());
        _materialOfPhase.setDelivered_duty_paid(materialOfPhase.getDelivered_duty_paid());
    }

    public boolean updateMaterialOfPhase(MaterialOfPhase materialOfPhase) {
        MaterialOfPhase _materialOfPhase = materialOfPhaseRepository
                .getOneMaterialOfOnePhase(materialOfPhase.getMop_id());
        if (_materialOfPhase != null) {
            _materialOfPhase.setPhase_id(materialOfPhase.getPhase_id());
            _materialOfPhase.setMaterial_id(materialOfPhase.getMaterial_id());
            _materialOfPhase.setMaterial_percent(materialOfPhase.getMaterial_percent());
            _materialOfPhase.setDelivered_duty_paid(materialOfPhase.getDelivered_duty_paid());
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
