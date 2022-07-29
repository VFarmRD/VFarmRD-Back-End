package com.example.vfarmrdbackend.model.formula;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "formulas")
public class Formula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formula_id;
    private int project_id;
    private int created_user_id;
    private String formula_pre_version;
    private String formula_version;
    private String formula_status;
    private float formula_cost;
    private float formula_weight;
    private Date created_time;
    private Date modified_time;
    private float volume;
    private float product_weight;
    private float density;
    private String description;
    private float loss;
    private String deny_reason;

    public Formula() {
    }

    public int getFormula_id() {
        return formula_id;
    }

    public void setFormula_id(int formula_id) {
        this.formula_id = formula_id;
    }

    public int getCreated_user_id() {
        return created_user_id;
    }

    public void setCreated_user_id(int created_user_id) {
        this.created_user_id = created_user_id;
    }

    public String getFormula_pre_version() {
        return formula_pre_version;
    }

    public void setFormula_pre_version(String formula_pre_version) {
        this.formula_pre_version = formula_pre_version;
    }

    public String getFormula_version() {
        return formula_version;
    }

    public void setFormula_version(String formula_version) {
        this.formula_version = formula_version;
    }

    public String getFormula_status() {
        return formula_status;
    }

    public void setFormula_status(String formula_status) {
        this.formula_status = formula_status;
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

}
