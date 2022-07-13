package com.example.vfarmrdbackend.payload;

public class TestUpdateRequest {
    private int test_id;
    private String test_content;
    private String test_expect;
    private boolean test_result;
    private int teststandardset_id;

    public TestUpdateRequest() {
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

    public int getTeststandardset_id() {
        return teststandardset_id;
    }

    public void setTeststandardset_id(int teststandardset_id) {
        this.teststandardset_id = teststandardset_id;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

}
