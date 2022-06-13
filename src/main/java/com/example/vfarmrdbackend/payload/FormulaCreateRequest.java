package com.example.vfarmrdbackend.payload;

import java.util.List;

public class FormulaCreateRequest {
    private String product_id;
    private float formula_cost;
    private int formula_weight;
    private List<PhaseCreateRequest> phaseCreateRequest;

    public FormulaCreateRequest() {
    }

    public FormulaCreateRequest(String product_id, float formula_cost, int formula_weight,
            List<PhaseCreateRequest> phaseCreateRequest) {
        this.product_id = product_id;
        this.formula_cost = formula_cost;
        this.formula_weight = formula_weight;
        this.phaseCreateRequest = phaseCreateRequest;
    }

    public int getFormula_weight() {
        return formula_weight;
    }

    public void setFormula_weight(int formula_weight) {
        this.formula_weight = formula_weight;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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
