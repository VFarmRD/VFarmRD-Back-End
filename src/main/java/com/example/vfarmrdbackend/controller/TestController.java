package com.example.vfarmrdbackend.controller;

import java.util.List;

import com.example.vfarmrdbackend.model.Test;
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
    private ResponseEntity<?> getAllTestWithFormula_id(@RequestParam("formula_id") int formula_id) {
        try {
            List<Test> _listTests = testService.getAllTestWithFormula_id(formula_id);
            if (_listTests != null) {
                return ResponseEntity.status(HttpStatus.OK).body(_listTests);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found any test!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @GetMapping("/tests/{id}")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> getTestByTest_id(@PathVariable("id") int test_id) {
        try {
            Test _tests = testService.getTestWithTest_id(test_id);
            if (_tests != null) {
                return ResponseEntity.status(HttpStatus.OK).body(_tests);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found test!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PostMapping("/tests/create")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> createTest(@RequestBody Test test) {
        try {
            testService.createTest(test);
            return ResponseEntity.status(HttpStatus.OK).body("Create new test completed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PutMapping("/tests/update")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> updateTest(@RequestBody Test test) {
        try {
            if (testService.updateTest(test)) {
                return ResponseEntity.status(HttpStatus.OK).body("Update test successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @DeleteMapping("/tests/delete/{id}")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> deleteTest(@PathVariable("id") int test_id) {
        try {
            if (testService.deleteTest(test_id)) {
                return ResponseEntity.status(HttpStatus.OK).body("Delete test successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

}
