package com.example.vfarmrdbackend.controllers;

import java.util.List;

import com.example.vfarmrdbackend.models.Formula;
import com.example.vfarmrdbackend.models.Test;
import com.example.vfarmrdbackend.repositories.TestRepository;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class TestController {
    @Autowired
    private TestRepository repo;

    // @GetMapping("/tests/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getTestById(@PathVariable("id") int id) {
        Test _test = repo.getTestByTest_id(id);
        if (_test != null) {
            return new ResponseEntity<>(_test, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Test not found!", HttpStatus.NOT_FOUND);
        }
    }

    // @GetMapping("/tests")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getTestWithFormula_id(@RequestBody Formula formula) {
        try {
            List<Test> _listTests = repo.getTestWithFormula_id(formula.getFormula_id());
            if (_listTests.isEmpty()) {
                return new ResponseEntity<>(
                        "Can't found any test!",
                        HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_listTests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PostMapping("/tests/create")
    @PreAuthorize("hasAuthority('staff')")

    public ResponseEntity<?> createTest(@RequestBody Test test) {
        try {
            repo.save(test);
            return new ResponseEntity<>(
                    "Create new test completed!",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PutMapping("/tests/update/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> updateTest(@PathVariable("id") int id, @RequestBody Test test) {
        Test _test = repo.getTestByTest_id(id);
        if (_test != null) {
            _test.setFile_id(test.getFile_id());
            _test.setTest_status(test.isTest_status());
            repo.save(_test);
            return new ResponseEntity<>("Update test successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @DeleteMapping("/tests/delete/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> deleteTest(@PathVariable("id") int id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>("Delete test successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
