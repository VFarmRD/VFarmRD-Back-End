package com.example.vfarmrdbackend.payload.test;

import java.util.List;

public class TestCreateRequest {
    private int formula_id;
    private List<TestCreateValue> listTestCreateValues;

    public TestCreateRequest() {
    }

    public int getFormula_id() {
        return formula_id;
    }

    public void setFormula_id(int formula_id) {
        this.formula_id = formula_id;
    }

    public List<TestCreateValue> getListTestCreateValues() {
        return listTestCreateValues;
    }

    public void setListTestCreateValues(List<TestCreateValue> listTestCreateValues) {
        this.listTestCreateValues = listTestCreateValues;
    }

}
