package com.example.vfarmrdbackend.controller;

import java.util.List;

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

import com.example.vfarmrdbackend.model.MaterialConflict;
import com.example.vfarmrdbackend.payload.MaterialConflictRequest;
import com.example.vfarmrdbackend.payload.MessageResponse;
import com.example.vfarmrdbackend.service.MaterialConflictService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "MaterialConflict", description = "The MaterialConflict's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class MaterialConflictController {
    @Autowired
    MaterialConflictService materialConflictService;

    @GetMapping("/materialconflicts")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getAllMaterialConflict() {
        try {
            List<MaterialConflict> listMaterialConflict = materialConflictService.getAllMaterialConflict();
            if (listMaterialConflict != null) {
                return ResponseEntity.status(HttpStatus.OK).body(listMaterialConflict);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy nguyên liệu xung đột nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/materialconflicts/{materialconflict_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getMaterialConflictById(@PathVariable("materialconflict_id") int materialconflict_id) {
        try {
            MaterialConflict materialconflicts = materialConflictService.getMaterialConflictById(materialconflict_id);
            if (materialconflicts != null) {
                return ResponseEntity.status(HttpStatus.OK).body(materialconflicts);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy nguyên liệu xung đột nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/materialconflicts/{material_id}/material")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getMaterialConflictByFirstMaterialId(@PathVariable("material_id") String material_id) {
        try {
            MaterialConflict materialconflicts = materialConflictService
                    .getMaterialConflictByFirstMaterialId(material_id);
            if (materialconflicts != null) {
                return ResponseEntity.status(HttpStatus.OK).body(materialconflicts);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy nguyên liệu xung đột nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/materialconflicts")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> createMaterialConflict(@RequestBody MaterialConflictRequest request) {
        try {
            materialConflictService.createMaterialConflict(request);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Tạo nguyên liệu xung đột thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/materialconflicts/{materialconflict_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> updateMaterialConflict(@PathVariable("materialconflict_id") int materialconflict_id,
            @RequestBody MaterialConflictRequest request) {
        try {
            if (materialConflictService.updateMaterialConflict(materialconflict_id, request)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new MessageResponse("Thành công", "Cập nhật nguyên liệu xung đột thành công!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy nguyên liệu xung đột nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/materialconflicts/{materialconflict_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> deleteMaterialConflict(@PathVariable("materialconflict_id") int materialconflict_id) {
        try {

            if (materialConflictService.deleteMaterialConflict(materialconflict_id)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new MessageResponse("Thành công", "Xóa nguyên liệu xung đột thành công!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy nguyên liệu xung đột nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

}
