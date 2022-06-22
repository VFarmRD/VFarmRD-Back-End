package com.example.vfarmrdbackend.payload;

import java.util.List;

public class FormulaUpdateRequest {
    private float formula_cost;
    private int formula_weight;
    private List<PhaseUpdateRequest> phaseUpdateRequest;

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

    public List<PhaseUpdateRequest> getPhaseUpdateRequest() {
        return phaseUpdateRequest;
    }

    public void setPhaseUpdateRequest(List<PhaseUpdateRequest> phaseUpdateRequest) {
        this.phaseUpdateRequest = phaseUpdateRequest;
    }

}
