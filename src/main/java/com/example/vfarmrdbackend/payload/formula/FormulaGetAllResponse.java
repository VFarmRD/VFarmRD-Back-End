package com.example.vfarmrdbackend.payload.formula;

import java.util.Date;

public class FormulaGetAllResponse {
    private int formula_id;
    private int project_id;
    private String project_name;
    private String formula_pre_version;
    private String formula_version;
    private String formula_status;
    private float formula_cost;
    private float formula_weight;
    private float user_id;
    private String user_name;
    private float volume;
    private float product_weight;
    private float density;
    private Date created_time;
    private Date modified_time;
    private String description;
    private float loss;

    public FormulaGetAllResponse() {
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

    public int getFormula_id() {
        return formula_id;
    }

    public void setFormula_id(int formula_id) {
        this.formula_id = formula_id;
    }

    public String getFormula_pre_version() {
        return formula_pre_version;
    }

    public void setFormula_pre_version(String formula_pre_version) {
        this.formula_pre_version = formula_pre_version;
    }

    public String getFormula_status() {
        return formula_status;
    }

    public void setFormula_status(String formula_status) {
        this.formula_status = formula_status;
    }

    public String getFormula_version() {
        return formula_version;
    }

    public void setFormula_version(String formula_version) {
        this.formula_version = formula_version;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

}
