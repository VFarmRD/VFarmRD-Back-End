package com.example.vfarmrdbackend.controller.tool;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.vfarmrdbackend.payload.tool.ToolInPhaseRequest;
import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.tool.ToolInPhaseService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ToolInPhase", description = "The ToolInPhase's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class ToolInPhaseController {
    @Autowired
    ToolInPhaseService toolInPhaseService;

    @GetMapping("/toolinphases")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> getAllToolInPhase() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    toolInPhaseService.getAllToolInPhase());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/toolinphases/{toolinphase_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> getToolInPhase(@PathVariable("toolinphase_id") int toolinphase_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    toolInPhaseService.getToolInPhase(toolinphase_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/toolinphases")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> createToolInPhase(@RequestBody ToolInPhaseRequest request,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            toolInPhaseService.createToolInPhase(request, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Tạo công cụ trong pha thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/toolinphases/{toolinphase_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> updateToolInPhase(@PathVariable("toolinphase_id") int toolinphase_id,
            @RequestBody ToolInPhaseRequest request,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            toolInPhaseService.updateToolInPhase(toolinphase_id, request, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Cập nhật công cụ trong pha thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/toolinphases/{toolinphase_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> deleteToolInPhase(@PathVariable("toolinphase_id") int toolinphase_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            toolInPhaseService.deleteToolInPhase(toolinphase_id, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Xóa công cụ trong pha thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
