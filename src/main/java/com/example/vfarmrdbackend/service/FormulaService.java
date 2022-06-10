package com.example.vfarmrdbackend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Formula;
import com.example.vfarmrdbackend.model.MaterialOfPhase;
import com.example.vfarmrdbackend.model.Phase;
import com.example.vfarmrdbackend.payload.FormulaRequest;
import com.example.vfarmrdbackend.repository.FormulaRepository;
import com.example.vfarmrdbackend.repository.MaterialOfPhaseRepository;
import com.example.vfarmrdbackend.repository.PhaseRepository;

@Service
public class FormulaService {
    @Autowired
    private FormulaRepository formulaRepository;

    @Autowired
    private PhaseRepository phaseRepository;

    @Autowired
    private MaterialOfPhaseRepository materialOfPhaseRepository;

    Date date;

    public List<Formula> getAllFormulaByProduct_id(String product_id) {
        return formulaRepository.getAllFormulaByProduct_id(product_id);
    }

    public Formula getFormulaByFormula_id(int formula_id) {
        return formulaRepository.getFormulaByFormula_id(formula_id);
    }

    public void createFormula(FormulaRequest formulaRequest, String jwt) {
        date = new Date();
        Formula _formula = new Formula();
        _formula.setProduct_id(formulaRequest.getProduct_id());
        _formula.setCreated_user_id(JwtService.getUser_idFromToken(jwt));
        _formula.setFormula_pre_version("none");
        _formula.setFormula_version("1.0");
        _formula.setFormula_name(formulaRequest.getFormula_name());
        _formula.setFormula_status("on progress");
        _formula.setFormula_cost(formulaRequest.getFormula_cost());
        _formula.setCreated_time(date);
        formulaRepository.save(_formula);
        for (int i = 0; i < formulaRequest.getPhases().size(); i++) {
            Phase phase_request = formulaRequest.getPhases().get(i);
            Phase _phase = new Phase();
            _phase.setFormula_id(phase_request.getFormula_id());
            _phase.setFormula_version(phase_request.getFormula_version());
            _phase.setFormula_cost(phase_request.getFormula_cost());
            _phase.setPhase_description(phase_request.getPhase_description());
            phaseRepository.save(_phase);
            for (int j = 0; j < phase_request.getMaterialofphase().size(); j++) {
                MaterialOfPhase material_of_phase_request = phase_request.getMaterialofphase().get(j);
                MaterialOfPhase _materialOfPhase = new MaterialOfPhase();
                _materialOfPhase.setPhase_id(material_of_phase_request.getPhase_id());
                _materialOfPhase.setMaterial_id(material_of_phase_request.getMaterial_id());
                _materialOfPhase.setMaterial_percent(material_of_phase_request.getMaterial_percent());
                _materialOfPhase.setDelivered_duty_paid(material_of_phase_request.getDelivered_duty_paid());
                materialOfPhaseRepository.save(_materialOfPhase);
            }
        }
    }

    public boolean setFormula_status(int formula_id, String status) {
        Formula _formula = formulaRepository.getFormulaByFormula_id(formula_id);
        if (_formula != null) {
            if (status.equals("approved") || status.equals("denied")) {
                if (!_formula.getFormula_status().equals("pending")) {
                    return false;
                }
            }
            date = new Date();
            _formula.setFormula_status(status);
            _formula.setModified_time(date);
            formulaRepository.save(_formula);
            return true;
        } else {
            return false;
        }
    }

    public boolean createAnotherFormula_version(FormulaRequest formulaRequest, String jwt, String type) {
        Formula _formula = formulaRepository.getFormulaByFormula_id(formulaRequest.getFormula_id());
        Formula newFormula = new Formula();
        date = new Date();
        if (_formula != null) {
            newFormula.setProduct_id(_formula.getProduct_id());
            newFormula.setCreated_user_id(JwtService.getUser_idFromToken(jwt));
            String formula_pre_version = _formula.getFormula_version();
            newFormula.setFormula_pre_version(formula_pre_version);
            String formula_now_version = formulaRepository.getLastestVersionOfProduct(_formula.getProduct_id());
            String splitString[] = formula_now_version.split("\\.");
            if (type.equals("update")) {
                formula_now_version = splitString[0] + "." + String.valueOf(Integer.parseInt(splitString[1]) + 1);
            } else if (type.equals("upgrade")) {
                formula_now_version = String.valueOf(Integer.parseInt(splitString[0]) + 1) + ".0";
            }
            newFormula.setFormula_version(formula_now_version);
            newFormula.setFormula_name(formulaRequest.getFormula_name());
            newFormula.setFormula_cost(formulaRequest.getFormula_cost());
            newFormula.setFormula_status("on progress");
            newFormula.setCreated_time(_formula.getCreated_time());
            newFormula.setModified_time(date);
            formulaRepository.save(newFormula);
            for (int i = 0; i < formulaRequest.getPhases().size(); i++) {
                Phase phase_request = formulaRequest.getPhases().get(i);
                Phase _phase = new Phase();
                _phase.setFormula_id(phase_request.getFormula_id());
                _phase.setFormula_version(phase_request.getFormula_version());
                _phase.setFormula_cost(phase_request.getFormula_cost());
                _phase.setPhase_description(phase_request.getPhase_description());
                phaseRepository.save(_phase);
                for (int j = 0; j < phase_request.getMaterialofphase().size(); j++) {
                    MaterialOfPhase material_of_phase_request = phase_request.getMaterialofphase().get(j);
                    MaterialOfPhase _materialOfPhase = new MaterialOfPhase();
                    _materialOfPhase.setPhase_id(material_of_phase_request.getPhase_id());
                    _materialOfPhase.setMaterial_id(material_of_phase_request.getMaterial_id());
                    _materialOfPhase.setMaterial_percent(material_of_phase_request.getMaterial_percent());
                    _materialOfPhase.setDelivered_duty_paid(material_of_phase_request.getDelivered_duty_paid());
                    materialOfPhaseRepository.save(_materialOfPhase);
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
