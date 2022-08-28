package com.example.vfarmrdbackend.service.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.vfarmrdbackend.service.user.UserInProjectService;
import com.example.vfarmrdbackend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.vfarmrdbackend.model.test.Test;
import com.example.vfarmrdbackend.model.user.User;
import com.example.vfarmrdbackend.model.file.File;
import com.example.vfarmrdbackend.model.notification.Notification;
import com.example.vfarmrdbackend.payload.file.response.FileResponse;
import com.example.vfarmrdbackend.payload.test.request.TestCreateRequest;
import com.example.vfarmrdbackend.payload.test.request.TestCreateValue;
import com.example.vfarmrdbackend.payload.test.request.TestUpdateMultipleRequest;
import com.example.vfarmrdbackend.payload.test.request.TestUpdateRequest;
import com.example.vfarmrdbackend.payload.test.response.TestGetResponse;
import com.example.vfarmrdbackend.repository.test.TestRepository;
import com.example.vfarmrdbackend.service.file.FileService;
import com.example.vfarmrdbackend.service.notification.NotificationService;
import com.example.vfarmrdbackend.service.others.JwtService;
import com.example.vfarmrdbackend.service.project.ProjectService;

@Service
public class TestService {
    @Autowired
    TestRepository testRepository;

    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserInProjectService userInProjectService;

    @Autowired
    ProjectService projectService;

    public List<Test> getAllTestWithFormula_id(int formula_id, String jwt) {
        try {
            return testRepository.getTestWithFormula_id(formula_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public TestGetResponse getTestWithTest_id(int test_id, String jwt) {
        try {
            Test test = testRepository.getTestByTest_id(test_id);
            User user = userService.getUserInfo(test.getUser_id());
            TestGetResponse response = new TestGetResponse();
            response.setTest_id(test_id);
            response.setTest_content(test.getTest_content());
            response.setUser_id(test.getUser_id());
            response.setUser_name(user.getUser_name());
            response.setUser_role(user.getRole_name());
            response.setTest_expect(test.getTest_expect());
            response.setTest_result(test.isTest_result());
            response.setObject_type("tests");
            File file = fileService.getFileByObjTypeAndId("tests", String.valueOf(test_id), jwt);
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
        } catch (Exception e) {
            throw e;
        }
    }

    public void createTest(TestCreateRequest testCreateRequest, String jwt) {
        try {
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
            List<Integer> listUser = userInProjectService.getAllUser_idInProjectWithProject_id(
                    projectService.getProjectByFormula_id(testCreateRequest.getFormula_id()).getProject_id());
            for (int j = 0; j < listUser.size(); j++) {
                notificationService.createNotification(new Notification(
                        listUser.get(j),
                        "Thông báo",
                        "Tiêu chuẩn của phiên bản "
                                + testRepository
                                        .getFormula_versionByFormula_idInTest(testCreateRequest.getFormula_id())
                                + " trong dự án "
                                + projectService.getProjectByFormula_id(testCreateRequest.getFormula_id())
                                        .getProject_name()
                                + " đã được tạo!",
                        new Date()));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateTest(int test_id, TestUpdateRequest request, String jwt) {
        try {
            Test test = testRepository.getTestByTest_id(test_id);
            if (test != null) {
                test.setTest_content(request.getTest_content());
                test.setTest_expect(request.getTest_expect());
                test.setTest_result(request.isTest_result());
                testRepository.save(test);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateMultipleTest(int formula_id, List<TestUpdateMultipleRequest> listRequest, String jwt) {
        try {
            TestCreateRequest createRequest = new TestCreateRequest();
            createRequest.setFormula_id(formula_id);
            List<TestCreateValue> listTestCreateValues = new ArrayList<>();
            createRequest.setListTestCreateValues(listTestCreateValues);
            List<Integer> listTest_id = testRepository.getTest_idWithFormula_id(formula_id);
            for (int i = 0; i < listRequest.size(); i++) {
                TestUpdateMultipleRequest request = listRequest.get(i);
                if (request.getTest_id() != 0) {
                    updateTest(request.getTest_id(),
                            new TestUpdateRequest(request.getTest_content(),
                                    request.getTest_expect(),
                                    request.isTest_result()),
                            jwt);
                    listTest_id.remove(Integer.valueOf(request.getTest_id()));
                } else if (request.getTest_id() == 0) {
                    TestCreateValue createValue = new TestCreateValue();
                    createValue.setTest_content(request.getTest_content());
                    createValue.setTest_expect(request.getTest_expect());
                    createValue.setTest_result(request.isTest_result());
                    listTestCreateValues.add(createValue);
                }
            }
            createTest(createRequest, jwt);
            for (int i = 0; i < listTest_id.size(); i++) {
                deleteTest(listTest_id.get(i), jwt);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean deleteTest(int test_id, String jwt) {
        try {
            Test test = testRepository.getTestByTest_id(test_id);
            if (test != null) {
                testRepository.delete(test);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public int getAllPassTestWithFormula_id(int formula_id, String jwt) {
        try {
            return testRepository.getAllPassTestWithFormula_id(formula_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public int getAllNotPassTestWithFormula_id(int formula_id, String jwt) {
        try {
            return testRepository.getAllNotPassTestWithFormula_id(formula_id);
        } catch (Exception e) {
            throw e;
        }
    }

}
