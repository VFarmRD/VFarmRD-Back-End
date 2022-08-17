package com.example.vfarmrdbackend.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vfarmrdbackend.payload.test.TestStandardRequest;
import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.test.TestStandardService;

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
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> getAllStandard() {
        try {
            return testStandardService.getAllStandard();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/teststandards/{teststandard_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> getStandard(@RequestParam("teststandard_id") int teststandard_id) {
        try {
            return testStandardService.getStandard(teststandard_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/teststandards")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> createStandard(
            @RequestParam(defaultValue = "", required = false) int teststandardset_id,
            @RequestBody TestStandardRequest testStandardRequest, @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return testStandardService.createStandard(teststandardset_id, testStandardRequest, jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/teststandards/{teststandard_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> updateStandard(@PathVariable("teststandard_id") int teststandard_id,
            @RequestBody TestStandardRequest testStandardRequest, @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return testStandardService.updateStandard(teststandard_id, testStandardRequest, jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/teststandards/{teststandard_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> deleteStandard(@RequestParam("teststandard_id") int teststandard_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return testStandardService.deleteStandard(teststandard_id, jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
