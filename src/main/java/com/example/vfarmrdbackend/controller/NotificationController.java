package com.example.vfarmrdbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vfarmrdbackend.payload.MessageResponse;
import com.example.vfarmrdbackend.payload.NotificationGetResponse;
import com.example.vfarmrdbackend.service.NotificationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notification", description = "The Notification's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notifications")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getAllNotifications() {
        try {
            List<NotificationGetResponse> listResponse = notificationService.getAllNotifications();
            if (listResponse != null) {
                return ResponseEntity.status(HttpStatus.OK).body(listResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy thông báo nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống gặp sự cố!"));
        }
    }

    @GetMapping("/notifications/users/{user_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getAllNotificationsOfUser_id(@PathVariable("user_id") int user_id) {
        try {
            List<NotificationGetResponse> listResponse = notificationService.getAllNotificationsOfUser(user_id);
            if (listResponse != null) {
                return ResponseEntity.status(HttpStatus.OK).body(listResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy thông báo nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống gặp sự cố!"));
        }
    }
}
