package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.TestStandard;
import com.example.vfarmrdbackend.model.TestStandardSet;
import com.example.vfarmrdbackend.payload.MessageResponse;
import com.example.vfarmrdbackend.payload.TestStandardRequest;
import com.example.vfarmrdbackend.payload.TestStandardSetCreateRequest;
import com.example.vfarmrdbackend.payload.TestStandardSetGetResponse;
import com.example.vfarmrdbackend.payload.TestStandardSetUpdateRequest;
import com.example.vfarmrdbackend.repository.TestStandardRepository;
import com.example.vfarmrdbackend.repository.TestStandardSetRepository;

@Service
public class TestStandardSetService {
    @Autowired
    TestStandardSetRepository testStandardSetRepository;

    @Autowired
    TestStandardRepository testStandardRepository;

    @Autowired
    TestStandardService testStandardService;

    public ResponseEntity<?> getAllStandardSet() {
        List<TestStandardSet> listTestStandardSet = testStandardSetRepository.findAll();
        if (listTestStandardSet != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    listTestStandardSet);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy Bộ Tiêu Chuẩn Thử Nghiệm nào!"));
        }
    }

    public ResponseEntity<?> getStandardSet(int teststandardset_id) {
        TestStandardSet testStandardSet = testStandardSetRepository.getTestStandardById(teststandardset_id);
        if (testStandardSet != null) {
            TestStandardSetGetResponse response = new TestStandardSetGetResponse();
            response.setTeststandardset_id(teststandardset_id);
            response.setTeststandardset_name(testStandardSet.getTeststandardset_name());
            response.setDescription(testStandardSet.getDescription());
            List<TestStandard> listTestStandard = testStandardRepository.getTestStandardWithSet_id(teststandardset_id);
            response.setTestStandard(listTestStandard);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy bộ Tiêu Chuẩn Thử Nghiệm nào!"));
        }
    }

    public ResponseEntity<?> createStandardSet(TestStandardSetCreateRequest request) {
        TestStandardSet newTestStandardSet = new TestStandardSet();
        newTestStandardSet.setTeststandardset_name(request.getTeststandardset_name());
        newTestStandardSet.setDescription(request.getDescription());
        List<TestStandardRequest> listTestStandard = request.getTestStandardRequest();
        for (int i = 0; i < listTestStandard.size(); i++) {
            testStandardService.createStandard(listTestStandard.get(i));
        }
        testStandardSetRepository.save(newTestStandardSet);
        return ResponseEntity.status(HttpStatus.OK).body(
                new MessageResponse("Thành công", "Tạo bộ Tiêu Chuẩn Thử Nghiệm mới thành công!"));
    }

    public ResponseEntity<?> updateStandardSet(TestStandardSetUpdateRequest request, int teststandardset_id) {
        TestStandardSet updateTestStandardSet = testStandardSetRepository.getTestStandardById(teststandardset_id);
        if (updateTestStandardSet != null) {
            updateTestStandardSet.setTeststandardset_name(request.getTeststandardset_name());
            updateTestStandardSet.setDescription(request.getDescription());
            List<TestStandard> listTestStandard = request.getTestStandard();
            List<Integer> listTestStandard_id = testStandardRepository.getTestStandard_idWithSet_id(teststandardset_id);
            for (int i = 0; i < listTestStandard.size(); i++) {
                TestStandard testStandard = listTestStandard.get(i);
                if (testStandard.getTeststandard_id() != 0) {
                    TestStandardRequest updateTestStandard = new TestStandardRequest();
                    updateTestStandard.setTeststandard_name(testStandard.getTeststandard_name());
                    updateTestStandard.setDescription(testStandard.getDescription());
                    testStandardService.updateStandard(testStandard.getTeststandard_id(), updateTestStandard);
                    listTestStandard_id.remove(Integer.valueOf(testStandard.getTeststandard_id()));
                } else if (testStandard.getTeststandard_id() == 0) {
                    TestStandardRequest newTestStandard = new TestStandardRequest();
                    newTestStandard.setTeststandard_name(testStandard.getTeststandard_name());
                    newTestStandard.setDescription(testStandard.getDescription());
                    newTestStandard.setTeststandardset_id(teststandardset_id);
                    testStandardService.createStandard(newTestStandard);
                }
            }
            for (int i = 0; i < listTestStandard_id.size(); i++) {
                testStandardService.deleteStandard(listTestStandard_id.get(i));
            }
            testStandardSetRepository.save(updateTestStandardSet);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Cập nhật bộ Tiêu Chuẩn Thử Nghiệm thành công!"));

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy bộ Tiêu Chuẩn Thử Nghiệm nào!"));
        }
    }

    public ResponseEntity<?> deleteStandardSet(int teststandardset_id) {
        TestStandardSet testStandardSet = testStandardSetRepository.getTestStandardById(teststandardset_id);
        if (testStandardSet != null) {
            testStandardSetRepository.delete(testStandardSet);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Xóa bộ Tiêu Chuẩn Thử Nghiệm thành công!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy bộ Tiêu Chuẩn Thử Nghiệm nào!"));
        }
    }
}
