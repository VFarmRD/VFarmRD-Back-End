package com.example.vfarmrdbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Formula;
import com.example.vfarmrdbackend.model.MaterialOfPhase;
import com.example.vfarmrdbackend.model.Phase;
import com.example.vfarmrdbackend.model.User;
import com.example.vfarmrdbackend.payload.FormulaCreateOtherVersionRequest;
import com.example.vfarmrdbackend.payload.FormulaCreateRequest;
import com.example.vfarmrdbackend.payload.FormulaGetResponse;
import com.example.vfarmrdbackend.payload.MaterialOfPhaseGetResponse;
import com.example.vfarmrdbackend.payload.PhaseCreateRequest;
import com.example.vfarmrdbackend.payload.PhaseGetResponse;
import com.example.vfarmrdbackend.repository.FormulaRepository;
import com.example.vfarmrdbackend.repository.PhaseRepository;

@Service
public class FormulaService {
    @Autowired
    private FormulaRepository formulaRepository;

    @Autowired
    private PhaseRepository phaseRepository;

    @Autowired
    PhaseService phaseService;

    @Autowired
    MaterialOfPhaseService materialOfPhaseService;

    @Autowired
    UserService userService;

    Date date;

    public List<Formula> getAllFormulaByProduct_id(String product_id, String formula_status) {
        return formulaRepository.getAllFormulaByProduct_idAndStatus(product_id, formula_status);
    }

    public FormulaGetResponse getFormulaByFormula_id(int formula_id) {
        Formula _formula = formulaRepository.getFormulaByFormula_id(formula_id);
        FormulaGetResponse formulaGetResponse = new FormulaGetResponse();
        formulaGetResponse.setProduct_id(_formula.getProduct_id());
        formulaGetResponse.setFormula_weight(_formula.getFormula_weight());
        formulaGetResponse.setFormula_cost(_formula.getFormula_cost());
        formulaGetResponse.setUser_id(_formula.getCreated_user_id());
        User _user = userService.getUserInfo(_formula.getCreated_user_id());
        formulaGetResponse.setUser_fullname(_user.getFullname());
        List<Phase> _listPhases = phaseService.getAllPhaseByFormula_id(formula_id);
        List<PhaseGetResponse> listPhaseGetResponse = new ArrayList<>();
        for (int i = 0; i < _listPhases.size(); i++) {
            PhaseGetResponse _phaseGetResponse = new PhaseGetResponse();
            _phaseGetResponse.setPhase_id(_listPhases.get(i).getPhase_id());
            _phaseGetResponse.setPhase_name(String.valueOf(i + 1));
            _phaseGetResponse.setPhase_description(_listPhases.get(i).getPhase_description());
            List<MaterialOfPhase> _listMaterialOfPhases = materialOfPhaseService
                    .getAllMaterialOfPhase(_listPhases.get(i).getPhase_id());
            List<MaterialOfPhaseGetResponse> _listMaterialOfPhasesResponse = new ArrayList<>();
            for (int j = 0; j < _listMaterialOfPhases.size(); j++) {
                MaterialOfPhaseGetResponse materialOfPhaseResponse = new MaterialOfPhaseGetResponse();
                materialOfPhaseResponse.setMaterial_id(_listMaterialOfPhases.get(j).getMaterial_id());
                materialOfPhaseResponse.setMaterial_percent(_listMaterialOfPhases.get(j).getMaterial_percent());
                materialOfPhaseResponse.setMaterial_cost(_listMaterialOfPhases.get(j).getMaterial_cost());
                materialOfPhaseResponse.setMaterial_weight(_listMaterialOfPhases.get(j).getMaterial_weight());
                _listMaterialOfPhasesResponse.add(materialOfPhaseResponse);
            }
            _phaseGetResponse.setMaterialOfPhaseGetResponse(_listMaterialOfPhasesResponse);
            listPhaseGetResponse.add(_phaseGetResponse);
        }
        formulaGetResponse.setPhaseGetResponse(listPhaseGetResponse);
        return formulaGetResponse;
    }

    public void createFormula(FormulaCreateRequest formulaCreateRequest, String jwt) {
        date = new Date();
        Formula _formula = new Formula();
        _formula.setProduct_id(formulaCreateRequest.getProduct_id());
        _formula.setCreated_user_id(JwtService.getUser_idFromToken(jwt));
        _formula.setFormula_pre_version("none");
        _formula.setFormula_version("1.0");
        _formula.setFormula_status("on progress");
        _formula.setFormula_cost(formulaCreateRequest.getFormula_cost());
        _formula.setFormula_weight(formulaCreateRequest.getFormula_weight());
        _formula.setCreated_time(date);
        formulaRepository.save(_formula);
        for (int i = 0; i < formulaCreateRequest.getPhaseCreateRequest().size(); i++) {
            PhaseCreateRequest phaseCreateRequest = formulaCreateRequest.getPhaseCreateRequest().get(i);
            phaseService.createPhase(formulaRepository.getLatestFormula_id(),
                    phaseCreateRequest);
            for (int j = 0; j < phaseCreateRequest.getMaterialOfPhaseCreateRequest().size(); j++) {
                materialOfPhaseService.createMaterialOfPhase(phaseRepository.getLatestPhase_id(), phaseCreateRequest
                        .getMaterialOfPhaseCreateRequest().get(j));

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

    public boolean createAnotherFormula_version(FormulaCreateOtherVersionRequest FormulaCreateOtherVersionRequest,
            String jwt, String type) {
        Formula _formula = formulaRepository.getFormulaByFormula_id(FormulaCreateOtherVersionRequest.getFormula_id());
        Formula newFormula = new Formula();
        date = new Date();
        if (_formula != null) {
            newFormula.setProduct_id(_formula.getProduct_id());
            newFormula.setCreated_user_id(JwtService.getUser_idFromToken(jwt));
            String formula_pre_version = _formula.getFormula_version();
            newFormula.setFormula_pre_version(formula_pre_version);
            String formula_now_version = formulaRepository.getLatestVersionOfProduct(_formula.getProduct_id());
            String splitString[] = formula_now_version.split("\\.");
            if (type.equals("update")) {
                formula_now_version = splitString[0] + "." + String.valueOf(Integer.parseInt(splitString[1]) + 1);
            } else if (type.equals("upgrade")) {
                formula_now_version = String.valueOf(Integer.parseInt(splitString[0]) + 1) + ".0";
            }
            newFormula.setFormula_version(formula_now_version);
            newFormula.setFormula_cost(FormulaCreateOtherVersionRequest.getFormula_cost());
            newFormula.setFormula_weight(FormulaCreateOtherVersionRequest.getFormula_weight());
            newFormula.setFormula_status("on progress");
            newFormula.setCreated_time(_formula.getCreated_time());
            newFormula.setModified_time(date);
            formulaRepository.save(newFormula);
            for (int i = 0; i < FormulaCreateOtherVersionRequest.getPhaseCreateRequest().size(); i++) {
                PhaseCreateRequest phaseCreateRequest = FormulaCreateOtherVersionRequest.getPhaseCreateRequest().get(i);
                phaseService.createPhase(formulaRepository.getLatestFormula_id(), phaseCreateRequest);
                for (int j = 0; j < phaseCreateRequest.getMaterialOfPhaseCreateRequest().size(); j++) {
                    materialOfPhaseService.createMaterialOfPhase(phaseRepository.getLatestPhase_id(),
                            phaseCreateRequest.getMaterialOfPhaseCreateRequest().get(j));
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
