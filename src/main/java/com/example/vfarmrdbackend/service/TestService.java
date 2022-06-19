package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Test;
import com.example.vfarmrdbackend.payload.TestCreateRequest;
import com.example.vfarmrdbackend.payload.TestCreateValue;
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

    public void createTest(TestCreateRequest testCreateRequest, int user_id) {
        for (int i = 0; i < testCreateRequest.getListTestCreateValues().size(); i++) {
            TestCreateValue testCreateValue = testCreateRequest.getListTestCreateValues().get(i);
            Test test = new Test();
            test.setFormula_id(testCreateRequest.getFormula_id());
            test.setTest_content(testCreateValue.getTest_content());
            test.setUser_id(user_id);
            test.setTest_expect(testCreateValue.getTest_expect());
            test.setTest_result(testCreateValue.isTest_result());
            testRepository.save(test);
        }
    }

    public boolean updateTest(TestUpdateRequest testUpdateRequest, int test_id) {
        Test test = testRepository.getTestByTest_id(test_id);
        if (test != null) {
            test.setTest_content(testUpdateRequest.getTest_content());
            test.setTest_expect(testUpdateRequest.getTest_expect());
            test.setTest_result(testUpdateRequest.isTest_result());
            testRepository.save(test);
            return true;
        }
        return false;
    }

    public boolean deleteTest(int test_id) {
        Test test = testRepository.getTestByTest_id(test_id);
        if (test != null) {
            testRepository.delete(test);
            return true;
        }
        return false;
    }
}
