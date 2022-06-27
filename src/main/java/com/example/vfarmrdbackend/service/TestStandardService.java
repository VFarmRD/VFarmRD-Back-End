package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.TestStandard;
import com.example.vfarmrdbackend.payload.MessageResponse;
import com.example.vfarmrdbackend.payload.TestStandardRequest;
import com.example.vfarmrdbackend.repository.TestStandardRepository;

@Service
public class TestStandardService {

    @Autowired
    TestStandardRepository testStandardRepository;

    public ResponseEntity<?> getAllStandard() {
        List<TestStandard> listTestStandard = testStandardRepository.findAll();
        if (listTestStandard != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    listTestStandard);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy Tiêu Chuẩn Thử Nghiệm nào!"));
        }

    }

    public ResponseEntity<?> getStandard(int teststandard_id) {
        TestStandard testStandard = testStandardRepository.getTestStandard(teststandard_id);
        if (testStandard != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    testStandard);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy Tiêu Chuẩn Thử Nghiệm này!"));
        }
    }

    public ResponseEntity<?> createStandard(TestStandardRequest request) {
        TestStandard newTestStandard = new TestStandard();
        newTestStandard.setTeststandard_name(request.getTeststandard_name());
        newTestStandard.setDescription(request.getDescription());
        testStandardRepository.save(newTestStandard);
        return ResponseEntity.status(HttpStatus.OK).body(
                new MessageResponse("Thành công", "Tạo Tiêu Chuẩn Thử Nghiệm thành công!"));
    }

    public ResponseEntity<?> updateStandard(int teststandard_id, TestStandardRequest request) {
        TestStandard updateTestStandard = testStandardRepository.getTestStandard(teststandard_id);
        if (updateTestStandard != null) {
            updateTestStandard.setTeststandard_name(request.getTeststandard_name());
            updateTestStandard.setDescription(request.getDescription());
            testStandardRepository.save(updateTestStandard);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Thay đổi Tiêu Chuẩn Thử Nghiệm thành công!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy Tiêu Chuẩn Thử Nghiệm nào!"));
        }
    }

    public ResponseEntity<?> deleteStandard(int teststandard_id) {
        TestStandard deleteTestStandard = testStandardRepository.getTestStandard(teststandard_id);
        if (deleteTestStandard != null) {
            testStandardRepository.delete(deleteTestStandard);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Xóa Tiêu Chuẩn Thử Nghiệm thành công!"));

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse("Lỗi", "Không tìm thấy Tiêu Chuẩn Thử Nghiệm nào!"));
        }

    }
}
