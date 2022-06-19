package com.example.vfarmrdbackend.controller;

import java.util.List;

import com.example.vfarmrdbackend.model.Test;
import com.example.vfarmrdbackend.payload.TestCreateRequest;
import com.example.vfarmrdbackend.payload.TestUpdateRequest;
import com.example.vfarmrdbackend.service.JwtService;
import com.example.vfarmrdbackend.service.TestService;

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
@RequestMapping(path = "/api")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/tests")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getAllTestWithFormula_id(@RequestParam("formula_id") int formula_id) {
        try {
            List<Test> listTests = testService.getAllTestWithFormula_id(formula_id);
            if (listTests != null) {
                return ResponseEntity.status(HttpStatus.OK).body(listTests);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found any test!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    e.getMessage());
        }
    }

    @GetMapping("/tests/{test_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getTestByTest_id(@PathVariable("test_id") int test_id) {
        try {
            Test test = testService.getTestWithTest_id(test_id);
            if (test != null) {
                return ResponseEntity.status(HttpStatus.OK).body(test);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found test!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    e.getMessage());
        }
    }

    @PostMapping("/tests")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> createTest(@RequestBody TestCreateRequest testCreateRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            testService.createTest(testCreateRequest, JwtService.getUser_idFromToken(jwt));
            return ResponseEntity.status(HttpStatus.OK).body("Create new test completed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    e.getMessage());
        }
    }

    @PutMapping("/tests/{test_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> updateTest(@PathVariable("test_id") int test_id,
            @RequestBody TestUpdateRequest testUpdateRequest) {
        try {
            if (testService.updateTest(testUpdateRequest, test_id)) {
                return ResponseEntity.status(HttpStatus.OK).body("Update test successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    e.getMessage());
        }
    }

    @DeleteMapping("/tests/{test_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> deleteTest(@PathVariable("test_id") int test_id) {
        try {
            if (testService.deleteTest(test_id)) {
                return ResponseEntity.status(HttpStatus.OK).body("Delete test successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    e.getMessage());
        }
    }

}
