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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vfarmrdbackend.payload.test.TestStandardSetCreateRequest;
import com.example.vfarmrdbackend.payload.test.TestStandardSetUpdateRequest;
import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.test.TestStandardSetService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "TestStandardSet", description = "The TestStandardSet's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class TestStandardSetController {
    @Autowired
    TestStandardSetService testStandardSetService;

    @GetMapping("/teststandardsets")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> getAllStandardSet() {
        try {
            return testStandardSetService.getAllStandardSet();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/teststandardsets/{teststandardset_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> getStandardSet(@PathVariable("teststandardset_id") int teststandardset_id) {
        try {
            return testStandardSetService.getStandardSet(teststandardset_id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/teststandardsets")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> createStandardSet(@RequestBody TestStandardSetCreateRequest request,
            @RequestHeader("Authorization") String jwt) {
        try {
            return testStandardSetService.createStandardSet(request, jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/teststandardsets/{teststandardset_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> updateStandardSet(@PathVariable("teststandardset_id") int teststandardset_id,
            @RequestBody TestStandardSetUpdateRequest request, @RequestHeader("Authorization") String jwt) {
        try {
            return testStandardSetService.updateStandardSet(request, teststandardset_id, jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/teststandardsets/{teststandardset_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> deleteStandardSet(@RequestParam("teststandardset_id") int teststandardset_id,
            @RequestHeader("Authorization") String jwt) {
        try {
            return testStandardSetService.deleteStandardSet(teststandardset_id, jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
