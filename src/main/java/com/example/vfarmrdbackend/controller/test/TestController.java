package com.example.vfarmrdbackend.controller.test;

import java.util.List;

import com.example.vfarmrdbackend.payload.test.TestCreateRequest;
import com.example.vfarmrdbackend.payload.test.TestUpdateMultipleRequest;
import com.example.vfarmrdbackend.payload.test.TestUpdateRequest;
import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.test.TestService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test", description = "The Test's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/tests")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getAllTestWithFormula_id(@RequestParam("formula_id") int formula_id,
            @RequestHeader("Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(testService.getAllTestWithFormula_id(formula_id, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/tests/{test_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getTestByTest_id(@PathVariable("test_id") int test_id,
            @RequestHeader("Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(testService.getTestWithTest_id(test_id, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/tests")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> createTest(@RequestBody TestCreateRequest testCreateRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            testService.createTest(testCreateRequest, jwt);
            return ResponseEntity.status(HttpStatus.OK).body("Create new test completed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/tests/{test_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> updateTest(@PathVariable("test_id") int test_id,
            @RequestBody TestUpdateRequest request, @RequestHeader("Authorization") String jwt) {
        try {
            testService.updateTest(test_id, request, jwt);
            return ResponseEntity.status(HttpStatus.OK).body("Update test successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/tests/formulas/{formula_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> updateMultipleTestInFormula(@PathVariable("formula_id") int formula_id,
            @RequestBody List<TestUpdateMultipleRequest> listRequest, @RequestHeader("Authorization") String jwt) {
        try {
            testService.updateMultipleTest(formula_id, listRequest, jwt);
            return ResponseEntity.status(HttpStatus.OK).body("Update test successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/tests/{test_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> deleteTest(@PathVariable("test_id") int test_id,
            @RequestHeader("Authorization") String jwt) {
        try {
            if (testService.deleteTest(test_id, jwt)) {
                return ResponseEntity.status(HttpStatus.OK).body("Delete test successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

}
