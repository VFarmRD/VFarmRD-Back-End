package com.example.vfarmrdbackend.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "formulas")
public class Formula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formula_id;
    private String product_id;
    private int created_user_id;
    private String formula_pre_version;
    private String formula_version;
    private String formula_name;
    private String formula_status;
    private float formula_cost;
    private Date created_time;
    private Date modified_time;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "formula_id", nullable = false, insertable = false, updatable = false)
    private List<Phase> phase;

    public Formula() {
    }

    public Formula(int formula_id, String product_id, int created_user_id, String formula_pre_version,
            String formula_version, String formula_name, String formula_status, float formula_cost, Date created_time,
            Date modified_time, List<Phase> phase) {
        this.formula_id = formula_id;
        this.product_id = product_id;
        this.created_user_id = created_user_id;
        this.formula_pre_version = formula_pre_version;
        this.formula_version = formula_version;
        this.formula_name = formula_name;
        this.formula_status = formula_status;
        this.formula_cost = formula_cost;
        this.created_time = created_time;
        this.modified_time = modified_time;
        this.phase = phase;
    }

    public List<Phase> getPhase() {
        return phase;
    }

    public void setPhase(List<Phase> phase) {
        this.phase = phase;
    }

    public String getFormula_pre_version() {
        return formula_pre_version;
    }

    public void setFormula_pre_version(String formula_pre_version) {
        this.formula_pre_version = formula_pre_version;
    }

    public int getCreated_user_id() {
        return created_user_id;
    }

    public void setCreated_user_id(int created_user_id) {
        this.created_user_id = created_user_id;
    }

    public int getFormula_id() {
        return formula_id;
    }

    public void setFormula_id(int formula_id) {
        this.formula_id = formula_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getFormula_version() {
        return formula_version;
    }

    public void setFormula_version(String formula_version) {
        this.formula_version = formula_version;
    }

    public String getFormula_name() {
        return formula_name;
    }

    public void setFormula_name(String formula_name) {
        this.formula_name = formula_name;
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
