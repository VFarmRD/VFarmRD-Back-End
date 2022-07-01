package com.example.vfarmrdbackend.payload;

import java.util.List;

public class FormulaGetResponse {
    private String project_id;
    private float formula_cost;
    private float formula_weight;
    private float user_id;
    private String user_fullname;
    private List<PhaseGetResponse> phaseGetResponse;
    private String test_status;
    private List<TestGetResponse> listTestResponse;

    public FormulaGetResponse() {
    }

    public String getTest_status() {
        return test_status;
    }

    public void setTest_status(String test_status) {
        this.test_status = test_status;
    }

    public List<TestGetResponse> getListTestResponse() {
        return listTestResponse;
    }

    public void setListTestResponse(List<TestGetResponse> listTestResponse) {
        this.listTestResponse = listTestResponse;
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

  

    public float getFormula_weight() {
        return formula_weight;
    }

    public void setFormula_weight(float formula_weight) {
        this.formula_weight = formula_weight;
    }

    public float getUser_id() {
        return user_id;
    }

    public void setUser_id(float user_id) {
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
