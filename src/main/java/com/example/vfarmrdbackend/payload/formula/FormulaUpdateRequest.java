package com.example.vfarmrdbackend.payload.formula;

import com.example.vfarmrdbackend.payload.phase.PhaseUpdateRequest;

import java.util.List;

public class FormulaUpdateRequest {
    private float formula_cost;
    private float formula_weight;
    private float volume;
    private float product_weight;
    private float density;
    private String description;
    private float loss;
    private List<PhaseUpdateRequest> phaseUpdateRequest;

    public FormulaUpdateRequest() {
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(float product_weight) {
        this.product_weight = product_weight;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getFormula_cost() {
        return formula_cost;
    }

    public void setFormula_cost(float formula_cost) {
        this.formula_cost = formula_cost;
    }

    public List<PhaseUpdateRequest> getPhaseUpdateRequest() {
        return phaseUpdateRequest;
    }

    public void setPhaseUpdateRequest(List<PhaseUpdateRequest> phaseUpdateRequest) {
        this.phaseUpdateRequest = phaseUpdateRequest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLoss() {
        return loss;
    }

    public void setLoss(float loss) {
        this.loss = loss;
    }

    public float getFormula_weight() {
        return formula_weight;
    }

    public void setFormula_weight(float formula_weight) {
        this.formula_weight = formula_weight;
    }

}
