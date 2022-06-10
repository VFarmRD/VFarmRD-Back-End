package com.example.vfarmrdbackend.payload;

public class TestUpdateRequest {
    private int test_id;
    private int formula_id;
    private boolean test_status;
    private int file_id;

    public TestUpdateRequest() {
    }

    public TestUpdateRequest(int test_id, int formula_id, boolean test_status, int file_id) {
        this.test_id = test_id;
        this.formula_id = formula_id;
        this.test_status = test_status;
        this.file_id = file_id;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public int getFormula_id() {
        return formula_id;
    }

    public void setFormula_id(int formula_id) {
        this.formula_id = formula_id;
    }

    public boolean isTest_status() {
        return test_status;
    }

    public void setTest_status(boolean test_status) {
        this.test_status = test_status;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

}
