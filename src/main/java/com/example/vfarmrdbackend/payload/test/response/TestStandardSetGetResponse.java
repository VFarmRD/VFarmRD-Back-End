package com.example.vfarmrdbackend.payload.test;

import java.util.List;

import com.example.vfarmrdbackend.model.test.TestStandard;

public class TestStandardSetGetResponse {
    private int teststandardset_id;
    private String teststandardset_name;
    private String description;
    private List<TestStandard> testStandard;

    public TestStandardSetGetResponse() {
    }

    public int getTeststandardset_id() {
        return teststandardset_id;
    }

    public void setTeststandardset_id(int teststandardset_id) {
        this.teststandardset_id = teststandardset_id;
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

    public List<TestStandard> getTestStandard() {
        return testStandard;
    }

    public void setTestStandard(List<TestStandard> testStandard) {
        this.testStandard = testStandard;
    }

}
