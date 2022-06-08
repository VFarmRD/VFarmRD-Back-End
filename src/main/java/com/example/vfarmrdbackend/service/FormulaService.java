package com.example.vfarmrdbackend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Formula;
import com.example.vfarmrdbackend.payload.FormulaRequest;
import com.example.vfarmrdbackend.repository.FormulaRepository;

@Service
public class FormulaService {
    @Autowired
    private FormulaRepository formulaRepository;

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

    // làm thêm 1 cái check version
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
            return true;
        } else {
            return false;
        }
    }
}
