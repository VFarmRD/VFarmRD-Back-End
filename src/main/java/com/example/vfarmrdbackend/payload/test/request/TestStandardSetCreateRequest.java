package com.example.vfarmrdbackend.payload.test;

import java.util.List;

public class TestStandardSetCreateRequest {
    private String teststandardset_name;
    private String description;
    private List<TestStandardRequest> testStandardRequest;

    public TestStandardSetCreateRequest() {
    }

    public String getTeststandardset_name() {
        return teststandardset_name;
    }

    public void setTeststandardset_name(String teststandardset_name) {
        this.teststandardset_name = teststandardset_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TestStandardRequest> getTestStandardRequest() {
        return testStandardRequest;
    }

    public void setTestStandardRequest(List<TestStandardRequest> testStandardRequest) {
        this.testStandardRequest = testStandardRequest;
    }

}
