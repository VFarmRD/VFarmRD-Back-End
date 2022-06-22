package com.example.vfarmrdbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Formula;
import com.example.vfarmrdbackend.model.MaterialOfPhase;
import com.example.vfarmrdbackend.model.Phase;
import com.example.vfarmrdbackend.model.Test;
import com.example.vfarmrdbackend.model.User;
import com.example.vfarmrdbackend.payload.FormulaUpgradeRequest;
import com.example.vfarmrdbackend.payload.FormulaCreateRequest;
import com.example.vfarmrdbackend.payload.FormulaGetResponse;
import com.example.vfarmrdbackend.payload.FormulaUpdateRequest;
import com.example.vfarmrdbackend.payload.MaterialOfPhaseGetResponse;
import com.example.vfarmrdbackend.payload.PhaseCreateRequest;
import com.example.vfarmrdbackend.payload.PhaseGetResponse;
import com.example.vfarmrdbackend.payload.PhaseUpdateRequest;
import com.example.vfarmrdbackend.payload.TestGetResponse;
import com.example.vfarmrdbackend.repository.FormulaRepository;
import com.example.vfarmrdbackend.repository.PhaseRepository;
import com.example.vfarmrdbackend.repository.TestRepository;

@Service
public class FormulaService {
    @Autowired
    private FormulaRepository formulaRepository;

    @Autowired
    private PhaseRepository phaseRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    PhaseService phaseService;

    @Autowired
    MaterialOfPhaseService materialOfPhaseService;

    @Autowired
    UserService userService;

    @Autowired
    TestService testService;

    Date date;

    public List<Formula> getAllFormulaByProduct_id(String product_id, String formula_status) {
        return formulaRepository.getAllFormulaByProduct_idAndStatus(product_id, formula_status);
    }

    public FormulaGetResponse getFormulaByFormula_id(int formula_id) {
        Formula formula = formulaRepository.getFormulaByFormula_id(formula_id);
        FormulaGetResponse formulaGetResponse = new FormulaGetResponse();
        formulaGetResponse.setProduct_id(formula.getProduct_id());
        formulaGetResponse.setFormula_weight(formula.getFormula_weight());
        formulaGetResponse.setFormula_cost(formula.getFormula_cost());
        formulaGetResponse.setUser_id(formula.getCreated_user_id());
        User user = userService.getUserInfo(formula.getCreated_user_id());
        formulaGetResponse.setUser_fullname(user.getFullname());
        List<Phase> listPhases = phaseService.getAllPhaseByFormula_id(formula_id);
        List<PhaseGetResponse> listPhaseGetResponse = new ArrayList<>();
        for (int i = 0; i < listPhases.size(); i++) {
            PhaseGetResponse phaseGetResponse = new PhaseGetResponse();
            phaseGetResponse.setPhase_id(listPhases.get(i).getPhase_id());
            phaseGetResponse.setPhase_name(String.valueOf(i + 1));
            phaseGetResponse.setPhase_description(listPhases.get(i).getPhase_description());
            List<MaterialOfPhase> listMaterialOfPhases = materialOfPhaseService
                    .getAllMaterialOfPhase(listPhases.get(i).getPhase_id());
            List<MaterialOfPhaseGetResponse> listMaterialOfPhasesResponse = new ArrayList<>();
            for (int j = 0; j < listMaterialOfPhases.size(); j++) {
                MaterialOfPhaseGetResponse materialOfPhaseResponse = new MaterialOfPhaseGetResponse();
                materialOfPhaseResponse.setMop_id(listMaterialOfPhases.get(j).getMop_id());
                materialOfPhaseResponse.setMaterial_id(listMaterialOfPhases.get(j).getMaterial_id());
                materialOfPhaseResponse.setMaterial_percent(listMaterialOfPhases.get(j).getMaterial_percent());
                materialOfPhaseResponse.setMaterial_cost(listMaterialOfPhases.get(j).getMaterial_cost());
                materialOfPhaseResponse.setMaterial_weight(listMaterialOfPhases.get(j).getMaterial_weight());
                listMaterialOfPhasesResponse.add(materialOfPhaseResponse);
            }
            phaseGetResponse.setMaterialOfPhaseGetResponse(listMaterialOfPhasesResponse);
            listPhaseGetResponse.add(phaseGetResponse);
        }
        formulaGetResponse.setPhaseGetResponse(listPhaseGetResponse);
        List<TestGetResponse> listTestResponse = new ArrayList<>();
        List<Test> listTest = testRepository.getTestWithFormula_id(formula_id);
        for (int i = 0; i < listTest.size(); i++) {
            Test test = listTest.get(i);
            listTestResponse.add(testService.getTestWithTest_id(test.getTest_id()));
        }
        formulaGetResponse.setListTestResponse(listTestResponse);
        String test_status = "Not yet!";
        if (testRepository.getAllPassTestWithFormula_id(formula_id).size() != 0) {
            test_status = "Passed!";
        }
        if (testRepository.getAllNotPassTestWithFormula_id(formula_id).size() != 0) {
            test_status = "Failed!";
        }
        formulaGetResponse.setTest_status(test_status);
        return formulaGetResponse;
    }

    public void createFormula(FormulaCreateRequest formulaCreateRequest, String jwt) {
        date = new Date();
        Formula formula = new Formula();
        formula.setProduct_id(formulaCreateRequest.getProduct_id());
        formula.setCreated_user_id(JwtService.getUser_idFromToken(jwt));
        formula.setFormula_pre_version("none");
        formula.setFormula_version("v1");
        formula.setFormula_status("on progress");
        formula.setFormula_cost(formulaCreateRequest.getFormula_cost());
        formula.setFormula_weight(formulaCreateRequest.getFormula_weight());
        formula.setCreated_time(date);
        formulaRepository.save(formula);
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

    public boolean updateFormula(int formula_id, FormulaUpdateRequest formulaUpdateRequest) {
        Formula updateFormula = formulaRepository.getFormulaByFormula_id(formula_id);
        if (updateFormula != null) {
            updateFormula.setFormula_cost(formulaUpdateRequest.getFormula_cost());
            updateFormula.setFormula_weight(formulaUpdateRequest.getFormula_weight());
            List<PhaseUpdateRequest> listPhase = formulaUpdateRequest.getPhaseUpdateRequest();
            for (int i = 0; i < listPhase.size(); i++) {
                phaseService.updatePhase(listPhase.get(i));
            }
            formulaRepository.save(updateFormula);
            return true;
        }
        return false;
    }

    public boolean setFormula_status(int formula_id, String status) {
        Formula formula = formulaRepository.getFormulaByFormula_id(formula_id);
        if (formula != null) {
            if (status.equals("approved") || status.equals("denied")
                    || status.equals("pending") || status.equals("deleted")) {
                return false;
            }
            if (status.equals("approved") || status.equals("denied")) {
                if (!formula.getFormula_status().equals("pending")) {
                    return false;
                }
            }
            date = new Date();
            formula.setFormula_status(status);
            formula.setModified_time(date);
            formulaRepository.save(formula);
            return true;
        } else {
            return false;
        }
    }

    public boolean upgradeFormula(int formula_id, FormulaUpgradeRequest formulaUpgradeRequest, String jwt) {
        Formula formula = formulaRepository.getFormulaByFormula_id(formula_id);
        Formula newFormula = new Formula();
        date = new Date();
        if (formula != null && formula.getFormula_status().equals("approved")) {
            newFormula.setProduct_id(formula.getProduct_id());
            newFormula.setCreated_user_id(JwtService.getUser_idFromToken(jwt));
            newFormula.setFormula_pre_version(formula.getFormula_version());
            newFormula.setFormula_version(
                    "v" + String.valueOf(formulaRepository.getTotalFormulaOfProduct(formula.getProduct_id())));
            newFormula.setFormula_cost(formulaUpgradeRequest.getFormula_cost());
            newFormula.setFormula_weight(formulaUpgradeRequest.getFormula_weight());
            newFormula.setFormula_status("on progress");
            newFormula.setCreated_time(formula.getCreated_time());
            newFormula.setModified_time(date);
            formulaRepository.save(newFormula);
            for (int i = 0; i < formulaUpgradeRequest.getPhaseCreateRequest().size(); i++) {
                PhaseCreateRequest phaseCreateRequest = formulaUpgradeRequest.getPhaseCreateRequest().get(i);
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
