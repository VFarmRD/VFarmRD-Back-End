package com.example.vfarmrdbackend.payload.test;

public class TestUpdateRequest {
    private String test_content;
    private String test_expect;
    private boolean test_result;

    public TestUpdateRequest() {
    }

    public TestUpdateRequest(String test_content, String test_expect, boolean test_result) {
        this.test_content = test_content;
        this.test_expect = test_expect;
        this.test_result = test_result;
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
