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

import com.example.vfarmrdbackend.payload.tool.ToolCategoryRequest;
import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.tool.ToolCategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ToolCategory", description = "The ToolCategory's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class ToolCategoryController {
    @Autowired
    ToolCategoryService toolCategoryService;

    @GetMapping("/toolcategories/")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> getAllToolCategories() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    toolCategoryService.getAllToolCategories());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/toolcategories/{toolcategory_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> getToolCategory(@PathVariable("toolcategory_id") int toolcategory_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    toolCategoryService.getToolCategory(toolcategory_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/toolcategories")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> createToolCategory(@RequestHeader("Authorization") String jwt,
            @RequestBody ToolCategoryRequest request) {
        try {
            toolCategoryService.createToolCategory(request, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Tạo loại công cụ mới thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/toolcategories/{toolcategory_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> updateToolCategory(@PathVariable("toolcategory_id") int toolcategory_id,
            @RequestHeader("Authorization") String jwt,
            @RequestBody ToolCategoryRequest request) {
        try {
            toolCategoryService.updateToolCategory(toolcategory_id, request, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Cập nhật công cụ thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/toolcategories/{toolcategory_id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('manager')")
    public ResponseEntity<?> deleteToolCategory(@PathVariable("toolcategory_id") int toolcategory_id,
            @RequestHeader("Authorization") String jwt) {
        try {
            toolCategoryService.deleteToolCategory(toolcategory_id, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Xóa công cụ thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

}
