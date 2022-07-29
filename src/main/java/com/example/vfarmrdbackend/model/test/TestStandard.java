package com.example.vfarmrdbackend.model.test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teststandards")
public class TestStandard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teststandard_id;
    private String teststandard_name;
    private String description;
    private int teststandardset_id;

    public TestStandard() {
    }

    public int getTeststandard_id() {
        return teststandard_id;
    }

    public void setTeststandard_id(int teststandard_id) {
        this.teststandard_id = teststandard_id;
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

    public int getTeststandardset_id() {
        return teststandardset_id;
    }

    public void setTeststandardset_id(int teststandardset_id) {
        this.teststandardset_id = teststandardset_id;
    }

}
