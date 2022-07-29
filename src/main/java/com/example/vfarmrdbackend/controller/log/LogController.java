package com.example.vfarmrdbackend.controller.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vfarmrdbackend.payload.log.LogGetResponse;
import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.log.LogService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Log", description = "The Log's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class LogController {
    @Autowired
    LogService logService;

    @GetMapping("/logs")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getAllLogs() {
        try {
            List<LogGetResponse> listResponse = logService.getAllLogs();
            if (listResponse != null) {
                return ResponseEntity.status(HttpStatus.OK).body(listResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy dòng nhật ký nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống gặp sự cố!"));
        }
    }
}
