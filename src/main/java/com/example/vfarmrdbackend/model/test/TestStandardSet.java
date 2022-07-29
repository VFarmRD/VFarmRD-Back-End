package com.example.vfarmrdbackend.model.test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teststandardsets")
public class TestStandardSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teststandardset_id;
    private String teststandardset_name;
    private String description;

    public TestStandardSet() {
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

}
