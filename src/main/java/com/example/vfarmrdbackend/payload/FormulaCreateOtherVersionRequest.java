package com.example.vfarmrdbackend.payload;

import java.util.List;

public class FormulaCreateOtherVersionRequest {
    private int formula_id;
    private float formula_cost;
    private List<PhaseCreateRequest> phaseCreateRequest;

    public FormulaCreateOtherVersionRequest() {
    }

    public FormulaCreateOtherVersionRequest(int formula_id, float formula_cost,
            List<PhaseCreateRequest> phaseCreateRequest) {
        this.formula_id = formula_id;
        this.formula_cost = formula_cost;
        this.phaseCreateRequest = phaseCreateRequest;
    }

    public int getFormula_id() {
        return formula_id;
    }

    public void setFormula_id(int formula_id) {
        this.formula_id = formula_id;
    }

    public float getFormula_cost() {
        return formula_cost;
    }

    public void setFormula_cost(float formula_cost) {
        this.formula_cost = formula_cost;
    }

    public List<PhaseCreateRequest> getPhaseCreateRequest() {
        return phaseCreateRequest;
    }

    public void setPhaseCreateRequest(List<PhaseCreateRequest> phaseCreateRequest) {
        this.phaseCreateRequest = phaseCreateRequest;
    }

}
