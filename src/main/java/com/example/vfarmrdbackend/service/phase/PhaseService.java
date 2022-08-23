package com.example.vfarmrdbackend.service.phase;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.vfarmrdbackend.model.material.MaterialOfPhase;
import com.example.vfarmrdbackend.model.phase.Phase;
import com.example.vfarmrdbackend.payload.material.MaterialOfPhaseCreateRequest;
import com.example.vfarmrdbackend.payload.material.MaterialOfPhaseUpdateRequest;
import com.example.vfarmrdbackend.payload.phase.PhaseCreateRequest;
import com.example.vfarmrdbackend.payload.phase.PhaseUpdateRequest;
import com.example.vfarmrdbackend.repository.material.MaterialOfPhaseRepository;
import com.example.vfarmrdbackend.repository.phase.PhaseRepository;
import com.example.vfarmrdbackend.service.tool.ToolInPhaseService;
import com.example.vfarmrdbackend.service.material.MaterialOfPhaseService;

@Service
public class PhaseService {
    @Autowired
    PhaseRepository phaseRepository;

    @Autowired
    MaterialOfPhaseRepository materialOfPhaseRepository;

    @Autowired
    MaterialOfPhaseService materialOfPhaseService;

    @Autowired
    ToolInPhaseService toolInPhaseService;

    public List<Phase> getAllPhaseByFormula_id(int formula_id) {
        try {
            return phaseRepository.getAllPhaseByFormula_id(formula_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public Phase getPhaseByPhase_id(int phase_id) {
        try {
            return phaseRepository.getPhaseByPhase_id(phase_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public void createPhase(int formula_id, PhaseCreateRequest phaseCreateRequest, String jwt) {
        try {
            Phase phase = new Phase();
            phase.setFormula_id(formula_id);
            phase.setPhase_index(phaseCreateRequest.getPhase_index());
            phase.setPhase_description(phaseCreateRequest.getPhase_description());
            phaseRepository.save(phase);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean updatePhase(PhaseUpdateRequest phaseUpdateRequest, String jwt) {
        try {
            Phase phase = phaseRepository.getPhaseByPhase_id(phaseUpdateRequest.getPhase_id());
            if (phase != null) {
                phase.setPhase_index(phaseUpdateRequest.getPhase_index());
                phase.setPhase_description(phaseUpdateRequest.getPhase_description());
                List<MaterialOfPhaseUpdateRequest> listMaterial = phaseUpdateRequest.getMaterialOfPhaseUpdateRequest();
                List<Integer> listOldMaterial = materialOfPhaseRepository
                        .getAllMaterial_idInPhase(phaseUpdateRequest.getPhase_id());
                for (int i = 0; i < listMaterial.size(); i++) {
                    MaterialOfPhaseUpdateRequest materialUpdate = listMaterial.get(i);
                    if (materialUpdate.getMop_id() != 0) {
                        materialOfPhaseService.updateMaterialOfPhase(materialUpdate, jwt);
                        listOldMaterial.remove(Integer.valueOf(materialUpdate.getMop_id()));
                    } else if (materialUpdate.getMop_id() == 0) {
                        MaterialOfPhaseCreateRequest materialCreate = new MaterialOfPhaseCreateRequest();
                        materialCreate.setMaterial_id(materialUpdate.getMaterial_id());
                        materialCreate.setMaterial_cost(materialUpdate.getMaterial_cost());
                        materialCreate.setMaterial_percent(materialUpdate.getMaterial_percent());
                        materialCreate.setMaterial_weight(materialUpdate.getMaterial_weight());
                        materialCreate.setMaterial_description(materialUpdate.getMaterial_description());
                        materialOfPhaseService.createMaterialOfPhase(phaseUpdateRequest.getPhase_id(),
                                materialCreate, jwt);
                    }
                }
                for (int i = 0; i < listOldMaterial.size(); i++) {
                    materialOfPhaseService.deleteMaterialOfPhase(listOldMaterial.get(i), jwt);
                }
                phaseRepository.save(phase);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean deletePhase(int phase_id, String jwt) {
        try {
            Phase phase = phaseRepository.getPhaseByPhase_id(phase_id);
            if (phase != null) {
                toolInPhaseService.deleteAllToolInPhaseByPhase_id(phase_id, jwt);
                List<MaterialOfPhase> listMaterialOfPhases = materialOfPhaseService.getAllMaterialOfPhase(phase_id);
                for (int i = 0; i < listMaterialOfPhases.size(); i++) {
                    materialOfPhaseService.deleteMaterialOfPhase(listMaterialOfPhases.get(i).getMop_id(), jwt);
                }
                phaseRepository.delete(phase);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public int getNewestPhase_id(String jwt) {
        try {
            return phaseRepository.getLatestPhase_id();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Integer> getAllPhase_idOfFormula(int formula_id, String jwt) {
        try {
            return phaseRepository.getAllPhase_idOfFormula(formula_id);
        } catch (Exception e) {
            throw e;
        }
    }
}
