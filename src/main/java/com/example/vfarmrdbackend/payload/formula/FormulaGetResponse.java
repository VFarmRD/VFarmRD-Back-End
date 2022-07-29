package com.example.vfarmrdbackend.payload.formula;

import com.example.vfarmrdbackend.payload.phase.PhaseGetResponse;
import com.example.vfarmrdbackend.payload.test.TestGetResponse;

import java.util.Date;
import java.util.List;

public class FormulaGetResponse {
    private int project_id;
    private String formula_version;
    private float formula_cost;
    private float formula_weight;
    private float user_id;
    private String user_name;
    private float volume;
    private float product_weight;
    private float density;
    private String description;
    private float loss;
    private String deny_reason;
    private List<PhaseGetResponse> phaseGetResponse;
    private String test_status;
    private List<TestGetResponse> listTestResponse;
    private Date created_time;
    private Date modified_time;

    public FormulaGetResponse() {
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

    public List<PhaseGetResponse> getPhaseGetResponse() {
        return phaseGetResponse;
    }

    public void setPhaseGetResponse(List<PhaseGetResponse> phaseGetResponse) {
        this.phaseGetResponse = phaseGetResponse;
    }

    public String getFormula_version() {
        return formula_version;
    }

    public void setFormula_version(String formula_version) {
        this.formula_version = formula_version;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getDeny_reason() {
        return deny_reason;
    }

    public void setDeny_reason(String deny_reason) {
        this.deny_reason = deny_reason;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getModified_time() {
        return modified_time;
    }

    public void setModified_time(Date modified_time) {
        this.modified_time = modified_time;
    }

}
