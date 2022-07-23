package com.example.vfarmrdbackend.payload.response;

public class TestResponse {
    private int test_id;
    private String test_content;
    private int user_id;
    private String test_expect;
    private boolean test_result;
    private String object_type;
    private int teststandardset_id;

    public TestResponse() {
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public String getTest_content() {
        return test_content;
    }

    public void setTest_content(String test_content) {
        this.test_content = test_content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public int getTeststandardset_id() {
        return teststandardset_id;
    }

    public void setTeststandardset_id(int teststandardset_id) {
        this.teststandardset_id = teststandardset_id;
    }

}
