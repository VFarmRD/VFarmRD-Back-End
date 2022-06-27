package com.example.vfarmrdbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vfarmrdbackend.payload.MessageResponse;
import com.example.vfarmrdbackend.payload.TestStandardRequest;
import com.example.vfarmrdbackend.service.TestStandardService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "TestStandard", description = "The TestStandard's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class TestStandardController {
    @Autowired
    TestStandardService testStandardService;

    @GetMapping("/teststandards")
    @PreAuthorize("hasAuthority('staff') or manager")
    public ResponseEntity<?> getAllStandard() {
        try {
            return testStandardService.getAllStandard();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/teststandards/{teststandard_id}")
    @PreAuthorize("hasAuthority('staff') or manager")
    public ResponseEntity<?> getStandard(@RequestParam("teststandard_id") int teststandard_id) {
        try {
            return testStandardService.getStandard(teststandard_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/teststandards")
    @PreAuthorize("hasAuthority('staff') or manager")
    public ResponseEntity<?> createStandard(@RequestBody TestStandardRequest testStandardRequest) {
        try {
            return testStandardService.createStandard(testStandardRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/teststandards/{teststandard_id}")
    @PreAuthorize("hasAuthority('staff') or manager")
    public ResponseEntity<?> updateStandard(@PathVariable("teststandard_id") int teststandard_id,
            @RequestBody TestStandardRequest testStandardRequest) {
        try {
            return testStandardService.updateStandard(teststandard_id, testStandardRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/teststandards/{teststandard_id}")
    @PreAuthorize("hasAuthority('staff') or manager")
    public ResponseEntity<?> deleteStandard(@RequestParam("teststandard_id") int teststandard_id) {
        try {
            return testStandardService.deleteStandard(teststandard_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
