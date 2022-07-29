package com.example.vfarmrdbackend.model.test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int test_id;
    private int formula_id;
    private String test_content;
    private int user_id;
    private String test_expect;
    private boolean test_result;

    public Test() {
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
}
