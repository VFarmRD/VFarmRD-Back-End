package com.example.vfarmrdbackend.payload;

import java.util.List;

public class FormulaCreateRequest {
    private String project_id;
    private float formula_cost;
    private int formula_weight;
    private float volume;
    private float product_weight;
    private float density;
    private String description;
    private List<PhaseCreateRequest> phaseCreateRequest;

    public FormulaCreateRequest() {
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

    public int getFormula_weight() {
        return formula_weight;
    }

    public void setFormula_weight(int formula_weight) {
        this.formula_weight = formula_weight;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
