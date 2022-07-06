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

import com.example.vfarmrdbackend.model.MaterialStandardPercent;
import com.example.vfarmrdbackend.payload.MaterialStandardPercentRequest;
import com.example.vfarmrdbackend.payload.MessageResponse;
import com.example.vfarmrdbackend.service.MaterialStandardPercentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "MaterialStandardPercent", description = "The MaterialStandardPercent's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class MaterialStandardPercentController {
    @Autowired
    MaterialStandardPercentService materialStandardPercentService;

    @GetMapping("/materialstandardpercents")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getAllMaterialStandardPercent() {
        try {
            List<MaterialStandardPercent> listMaterialStandardPercents = materialStandardPercentService
                    .getAllMaterialStandardPercent();
            if (listMaterialStandardPercents != null) {
                return ResponseEntity.status(HttpStatus.OK).body(listMaterialStandardPercents);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy tiêu chuẩn phần trăm nguyên liệu nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/materialstandardpercents/{msp_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getMaterialStandardPercentById(@PathVariable("msp_id") int msp_id) {
        try {
            MaterialStandardPercent materialstandardpercents = materialStandardPercentService
                    .getMaterialStandardPercent(msp_id);
            if (materialstandardpercents != null) {
                return ResponseEntity.status(HttpStatus.OK).body(materialstandardpercents);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy tiêu chuẩn phần trăm nguyên liệu nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/materialstandardpercents")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> createMaterialStandardPercent(@RequestBody MaterialStandardPercentRequest request) {
        try {
            materialStandardPercentService.createMaterialStandardPercent(request);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Tạo tiêu chuẩn phần trăm nguyên liệu thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/materialstandardpercents/{msp_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> updateMaterialStandardPercent(@PathVariable("msp_id") int msp_id,
            @RequestBody MaterialStandardPercentRequest request) {
        try {
            if (materialStandardPercentService.updateMaterialStandardPercent(msp_id, request)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new MessageResponse("Thành công", "Cập nhật tiêu chuẩn phần trăm nguyên liệu thành công!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy tiêu chuẩn phần trăm nguyên liệu nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/materialstandardpercents/{msp_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> deleteMaterialStandardPercent(@PathVariable("msp_id") int msp_id) {
        try {

            if (materialStandardPercentService.deleteMaterialStandardPercent(msp_id)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new MessageResponse("Thành công", "Xóa tiêu chuẩn phần trăm nguyên liệu thành công!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy tiêu chuẩn phần trăm nguyên liệu nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
