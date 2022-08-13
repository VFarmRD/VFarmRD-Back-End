package com.example.vfarmrdbackend.controller.material;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vfarmrdbackend.model.material.MaterialConflict;
import com.example.vfarmrdbackend.payload.material.MaterialConflictCreateRequest;
import com.example.vfarmrdbackend.payload.material.MaterialConflictUpdateRequest;
import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.material.MaterialConflictService;

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
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
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
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
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

    @GetMapping("/materialconflicts/")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getMaterialConflictByFirstMaterialId(@RequestParam("material_id") String material_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(materialConflictService
                    .getMaterialConflictByFirstMaterialId(material_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/materialconflicts")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> createMaterialConflict(@RequestBody List<MaterialConflictCreateRequest> request,
            @RequestHeader("Authorization") String jwt) {
        try {
            materialConflictService.createMaterialConflict(request, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Tạo nguyên liệu xung đột thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/materialconflicts/")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> updateMaterialConflict(@RequestBody List<MaterialConflictUpdateRequest> request,
            @RequestHeader("Authorization") String jwt) {
        try {
            materialConflictService.updateMaterialConflict(request, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Cập nhật nguyên liệu xung đột thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/materialconflicts/{materialconflict_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> deleteMaterialConflict(@PathVariable("materialconflict_id") int materialconflict_id,
            @RequestHeader("Authorization") String jwt) {
        try {

            if (materialConflictService.deleteMaterialConflict(materialconflict_id, jwt)) {
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
