package com.example.vfarmrdbackend.payload;

import java.util.List;

public class FormulaUpdateRequest {
    private float formula_cost;
    private int formula_weight;
    private List<PhaseUpdateRequest> phaseCreateRequest;

    public FormulaUpdateRequest() {
    }

    public float getFormula_cost() {
        return formula_cost;
    }

    public void setFormula_cost(float formula_cost) {
        this.formula_cost = formula_cost;
    }

    public int getFormula_weight() {
        return formula_weight;
    }

    public void setFormula_weight(int formula_weight) {
        this.formula_weight = formula_weight;
    }

    public List<PhaseUpdateRequest> getPhaseCreateRequest() {
        return phaseCreateRequest;
    }

    public void setPhaseCreateRequest(List<PhaseUpdateRequest> phaseCreateRequest) {
        this.phaseCreateRequest = phaseCreateRequest;
    }

}
