package com.example.vfarmrdbackend.payload;

public class TestCreateRequest {
    private int formula_id;
    private String test_content;
    private String test_expect;
    private boolean test_result;

    public TestCreateRequest() {
    }

    public int getFormula_id() {
        return formula_id;
    }

    public void setFormula_id(int formula_id) {
        this.formula_id = formula_id;
    }

    public String getTest_content() {
        return test_content;
    }

    public void setTest_content(String test_content) {
        this.test_content = test_content;
    }

    public String getTest_expect() {
        return test_expect;
    }

    public void setTest_expect(String test_expect) {
        this.test_expect = test_expect;
    }

    public boolean isTest_result() {
        return test_result;
    }

    public void setTest_result(boolean test_result) {
        this.test_result = test_result;
    }

}
