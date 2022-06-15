package com.example.vfarmrdbackend.payload;

import java.util.List;

public class FormulaGetResponse {
    private String product_id;
    private float formula_cost;
    private int formula_weight;
    private int user_id;
    private String user_fullname;
    private List<PhaseGetResponse> phaseGetResponse;

    public FormulaGetResponse() {
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

    public int getFormula_weight() {
        return formula_weight;
    }

    public void setFormula_weight(int formula_weight) {
        this.formula_weight = formula_weight;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public List<PhaseGetResponse> getPhaseGetResponse() {
        return phaseGetResponse;
    }

    public void setPhaseGetResponse(List<PhaseGetResponse> phaseGetResponse) {
        this.phaseGetResponse = phaseGetResponse;
    }

}
