package com.example.vfarmrdbackend.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.business.model.Phase;
import com.example.vfarmrdbackend.data.repository.PhaseRepository;

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

    public void createPhase(Phase phase) {
        Phase _phase = new Phase();
        _phase.setFormula_id(phase.getFormula_id());
        _phase.setFormula_version(phase.getFormula_version());
        _phase.setFormula_cost(phase.getFormula_cost());
        _phase.setPhase_description(phase.getPhase_description());
        phaseRepository.save(_phase);
    }

    public boolean updatePhase(Phase phase) {
        Phase _phase = phaseRepository.getPhaseByPhase_id(phase.getPhase_id());
        if (_phase != null) {
            _phase.setFormula_id(phase.getFormula_id());
            _phase.setFormula_version(phase.getFormula_version());
            _phase.setFormula_cost(phase.getFormula_cost());
            _phase.setPhase_description(phase.getPhase_description());
            phaseRepository.save(_phase);
            return true;
        } else {
            return false;
        }
    }

    public void deletePhase(int phase_id) {
        Phase _phase = phaseRepository.getPhaseByPhase_id(phase_id);
        phaseRepository.delete(_phase);
    }
}
