package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Phase;
import com.example.vfarmrdbackend.payload.PhaseCreateRequest;
import com.example.vfarmrdbackend.payload.PhaseUpdateRequest;
import com.example.vfarmrdbackend.repository.PhaseRepository;

@Service
public class PhaseService {
    @Autowired
    private PhaseRepository phaseRepository;

    public List<Phase> getAllPhaseByFormula_id(int formula_id) {
        return phaseRepository.getAllPhaseByFormula_id(formula_id);
    }

    public Phase getPhaseByPhase_id(int phase_id) {
        return phaseRepository.getPhaseByPhase_id(phase_id);
    }

    public void createPhase(int formula_id, PhaseCreateRequest phaseCreateRequest) {
        Phase phase = new Phase();
        phase.setFormula_id(formula_id);
        phase.setPhase_description(phaseCreateRequest.getPhase_description());
        phaseRepository.save(phase);
    }

    public boolean updatePhase(PhaseUpdateRequest phaseUpdateRequest) {
        Phase phase = phaseRepository.getPhaseByPhase_id(phaseUpdateRequest.getPhase_id());
        if (phase != null) {
            phase.setPhase_description(phaseUpdateRequest.getPhase_description());
            phaseRepository.save(phase);
            return true;
        } else {
            return false;
        }
    }

    public boolean deletePhase(int phase_id) {
        Phase phase = phaseRepository.getPhaseByPhase_id(phase_id);
        if (phase != null) {
            phaseRepository.delete(phase);
            return true;
        } else {
            return false;
        }
    }
}
