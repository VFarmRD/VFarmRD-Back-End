package com.example.vfarmrdbackend.service.phase;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.material.MaterialOfPhase;
import com.example.vfarmrdbackend.model.phase.Phase;
import com.example.vfarmrdbackend.model.error.ErrorModel;
import com.example.vfarmrdbackend.payload.material.MaterialOfPhaseCreateRequest;
import com.example.vfarmrdbackend.payload.material.MaterialOfPhaseUpdateRequest;
import com.example.vfarmrdbackend.payload.phase.PhaseCreateRequest;
import com.example.vfarmrdbackend.payload.phase.PhaseUpdateRequest;
import com.example.vfarmrdbackend.repository.material.MaterialOfPhaseRepository;
import com.example.vfarmrdbackend.repository.phase.PhaseRepository;
import com.example.vfarmrdbackend.service.tool.ToolInPhaseService;
import com.example.vfarmrdbackend.service.error.ErrorService;
import com.example.vfarmrdbackend.service.material.MaterialOfPhaseService;
import com.example.vfarmrdbackend.service.others.JwtService;

@Service
public class PhaseService {
    @Autowired
    private PhaseRepository phaseRepository;

    @Autowired
    private MaterialOfPhaseRepository materialOfPhaseRepository;

    @Autowired
    MaterialOfPhaseService materialOfPhaseService;

    @Autowired
    ToolInPhaseService toolInPhaseService;

    @Autowired
    ErrorService errorService;

    public List<Phase> getAllPhaseByFormula_id(int formula_id) {
        return phaseRepository.getAllPhaseByFormula_id(formula_id);
    }

    public Phase getPhaseByPhase_id(int phase_id) {
        return phaseRepository.getPhaseByPhase_id(phase_id);
    }

    public void createPhase(int formula_id, PhaseCreateRequest phaseCreateRequest, String jwt) {
        Phase phase = new Phase();
        phase.setFormula_id(formula_id);
        phase.setPhase_index(phaseCreateRequest.getPhase_index());
        phase.setPhase_description(phaseCreateRequest.getPhase_description());
        phaseRepository.save(phase);
    }

    public boolean updatePhase(PhaseUpdateRequest phaseUpdateRequest, String jwt) {
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
    }

    public boolean deletePhase(int phase_id, String jwt) {
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
    }

    public int getNewestPhase_id(String jwt) {
        try {
            return phaseRepository.getLatestPhase_id();
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PHASE GENERATE NEW ID",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public List<Integer> getAllPhase_idOfFormula(int formula_id, String jwt) {
        try {
            return phaseRepository.getAllPhase_idOfFormula(formula_id);
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "PHASE GET ALL ID WITH FORMULA",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }
}
