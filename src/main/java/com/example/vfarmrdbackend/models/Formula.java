package com.example.vfarmrdbackend.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "formulas")
public class Formula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formula_id ;
    private int product_id;
    private String formula_version;
    private String formula_name;
    private boolean formula_status;
    private float formula_cost;
    private Date created_time;
    private Date modified_time;

    public Formula() {
    }

    public Formula(int formula_id, int product_id, String formula_version, String formula_name, boolean formula_status, float formula_cost, Date created_time, Date modified_time) {
        this.formula_id = formula_id;
        this.product_id = product_id;
        this.formula_version = formula_version;
        this.formula_name = formula_name;
        this.formula_status = formula_status;
        this.formula_cost = formula_cost;
        this.created_time = created_time;
        this.modified_time = modified_time;
    }

    public int getFormula_id() {
        return formula_id;
    }

    public void setFormula_id(int formula_id) {
        this.formula_id = formula_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
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

    public boolean isFormula_status() {
        return formula_status;
    }

    public void setFormula_status(boolean formula_status) {
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
