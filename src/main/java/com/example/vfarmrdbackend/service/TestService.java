package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Test;
import com.example.vfarmrdbackend.payload.TestCreateRequest;
import com.example.vfarmrdbackend.payload.TestUpdateRequest;
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

    public void createTest(TestCreateRequest testCreateRequest) {
        Test _test = new Test();
        _test.setFormula_id(testCreateRequest.getFormula_id());
        _test.setTest_status(testCreateRequest.isTest_status());
        _test.setFile_id(testCreateRequest.getFile_id());
        testRepository.save(_test);
    }

    public boolean updateTest(TestUpdateRequest testUpdateRequest) {
        Test _test = testRepository.getTestByTest_id(testUpdateRequest.getTest_id());
        if (_test != null) {
            _test.setTest_status(testUpdateRequest.isTest_status());
            _test.setFile_id(testUpdateRequest.getFile_id());
            testRepository.save(_test);
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
