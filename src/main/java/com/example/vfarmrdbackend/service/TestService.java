package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Test;
import com.example.vfarmrdbackend.repository.TestRepository;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    public List<Test> getAllTestWithFormula_id(int formula_id) {
        return testRepository.getTestWithFormula_id(formula_id);
    }

    public Test getTestWithTest_id(int test_id) {
        return testRepository.getTestByTest_id(test_id);
    }

    public void createTest(Test test) {
        Test _test = new Test();
        _test.setFormula_id(test.getFormula_id());
        _test.setTest_status(test.isTest_status());
        _test.setFile_id(test.getFile_id());
    }

    public boolean updateTest(Test test) {
        Test _test = testRepository.getTestByTest_id(test.getTest_id());
        if (_test != null) {
            _test.setFormula_id(test.getFormula_id());
            _test.setTest_status(test.isTest_status());
            _test.setFile_id(test.getFile_id());
            return true;
        }
        return false;
    }

    public boolean deleteTest(int test_id) {
        Test _test = testRepository.getTestByTest_id(test_id);
        if (_test != null) {
            testRepository.delete(_test);
            return true;
        }
        return false;
    }
}
