package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.vfarmrdbackend.model.File;
import com.example.vfarmrdbackend.model.Test;
import com.example.vfarmrdbackend.model.User;
import com.example.vfarmrdbackend.payload.FileResponse;
import com.example.vfarmrdbackend.payload.TestCreateRequest;
import com.example.vfarmrdbackend.payload.TestCreateValue;
import com.example.vfarmrdbackend.payload.TestGetResponse;
import com.example.vfarmrdbackend.payload.TestUpdateRequest;
import com.example.vfarmrdbackend.repository.FileRepository;
import com.example.vfarmrdbackend.repository.TestRepository;
import com.example.vfarmrdbackend.repository.TestStandardRepository;
import com.example.vfarmrdbackend.repository.TestStandardSetRepository;
import com.example.vfarmrdbackend.repository.UserRepository;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TestStandardSetRepository testStandardSetRepository;

    @Autowired
    TestStandardRepository testStandardRepository;

    public List<Test> getAllTestWithFormula_id(int formula_id) {
        return testRepository.getTestWithFormula_id(formula_id);
    }

    public TestGetResponse getTestWithTest_id(int test_id) {
        Test test = testRepository.getTestByTest_id(test_id);
        User user = userRepository.getUserByUser_id(test.getUser_id());
        TestGetResponse response = new TestGetResponse();
        response.setTest_id(test_id);
        response.setTest_content(test.getTest_content());
        response.setUser_id(test.getUser_id());
        response.setUser_name(user.getUser_name());
        response.setUser_role(user.getRole_name());
        response.setTest_expect(test.getTest_expect());
        response.setTest_result(test.isTest_result());
        response.setObject_type("tests");
        File file = fileRepository.getFileByObjTypeAndId("tests", String.valueOf(test_id));
        if (file != null) {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/download/")
                    .path(String.valueOf(file.getFile_id()))
                    .toUriString();
            response.setFileResponse(new FileResponse(
                    file.getFile_name(),
                    fileDownloadUri,
                    file.getFile_type(),
                    file.getFile_data().length));
        }
        return response;
    }

    public void createTest(TestCreateRequest testCreateRequest, String jwt) {
        for (int i = 0; i < testCreateRequest.getListTestCreateValues().size(); i++) {
            TestCreateValue testCreateValue = testCreateRequest.getListTestCreateValues().get(i);
            Test test = new Test();
            test.setFormula_id(testCreateRequest.getFormula_id());
            test.setTest_content(testCreateValue.getTest_content());
            test.setUser_id(JwtService.getUser_idFromToken(jwt));
            test.setTest_expect(testCreateValue.getTest_expect());
            test.setTest_result(testCreateValue.isTest_result());
            testRepository.save(test);
        }
    }

    public void updateTest(List<TestUpdateRequest> listRequest, String jwt) {
        for (int i = 0; i < listRequest.size(); i++) {
            Test test = testRepository.getTestByTest_id(listRequest.get(i).getTest_id());
            if (test != null) {
                test.setTest_content(listRequest.get(i).getTest_content());
                test.setTest_expect(listRequest.get(i).getTest_expect());
                test.setTest_result(listRequest.get(i).isTest_result());
                testRepository.save(test);
            }
        }
    }

    public boolean deleteTest(int test_id, String jwt) {
        Test test = testRepository.getTestByTest_id(test_id);
        if (test != null) {
            testRepository.delete(test);
            return true;
        }
        return false;
    }
}
