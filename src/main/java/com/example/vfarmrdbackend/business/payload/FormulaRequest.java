package com.example.vfarmrdbackend.business.payload;

public class FormulaRequest {
    private int formula_id;
    private String product_id;
    private String formula_name;
    private float formula_cost;

    public FormulaRequest() {
    }

    public FormulaRequest(int formula_id, String product_id, String formula_name, float formula_cost) {
        this.formula_id = formula_id;
        this.product_id = product_id;
        this.formula_name = formula_name;
        this.formula_cost = formula_cost;
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

    public String getFormula_name() {
        return formula_name;
    }

    public void setFormula_name(String formula_name) {
        this.formula_name = formula_name;
    }

    public float getFormula_cost() {
        return formula_cost;
    }

    public void setFormula_cost(float formula_cost) {
        this.formula_cost = formula_cost;
    }

}
