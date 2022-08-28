package com.example.vfarmrdbackend.payload.test;

public class TestStandardUpdateRequest {
    private int teststandard_id;
    private String teststandard_name;
    private String description;

    public TestStandardUpdateRequest() {
    }

    public String getTeststandard_name() {
        return teststandard_name;
    }

    public void setTeststandard_name(String teststandard_name) {
        this.teststandard_name = teststandard_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTeststandard_id() {
        return teststandard_id;
    }

    public void setTeststandard_id(int teststandard_id) {
        this.teststandard_id = teststandard_id;
    }

}
