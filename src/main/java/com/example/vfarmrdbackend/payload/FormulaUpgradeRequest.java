package com.example.vfarmrdbackend.payload;

import java.util.List;

public class FormulaUpgradeRequest {
    private float formula_cost;
    private int formula_weight;
    private List<PhaseCreateRequest> phaseCreateRequest;

    public FormulaUpgradeRequest() {
    }

    public int getFormula_weight() {
        return formula_weight;
    }

    public void setFormula_weight(int formula_weight) {
        this.formula_weight = formula_weight;
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
