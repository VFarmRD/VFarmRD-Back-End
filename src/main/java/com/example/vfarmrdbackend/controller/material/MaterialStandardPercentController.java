package com.example.vfarmrdbackend.controller.material;

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

import com.example.vfarmrdbackend.payload.material.request.MaterialStandardPercentRequest;
import com.example.vfarmrdbackend.payload.others.response.MessageResponse;
import com.example.vfarmrdbackend.service.material.MaterialStandardPercentService;
import com.example.vfarmrdbackend.service.others.ValidatorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "MaterialStandardPercent", description = "The MaterialStandardPercent's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class MaterialStandardPercentController {
    @Autowired
    MaterialStandardPercentService materialStandardPercentService;

    @Autowired
    ValidatorService validatorService;

    @GetMapping("/materialstandardpercents")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getAllMaterialStandardPercent() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(materialStandardPercentService
                    .getAllMaterialStandardPercent());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/materialstandardpercents/{msp_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getMaterialStandardPercentById(@PathVariable("msp_id") int msp_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(materialStandardPercentService
                    .getMaterialStandardPercent(msp_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/materialstandardpercents/")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getMaterialStandardPercentByMaterial_id(@RequestParam("material_id") String material_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(materialStandardPercentService
                    .getMaterialStandardPercentByMaterial_id(material_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/materialstandardpercents")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> createMaterialStandardPercent(@RequestBody MaterialStandardPercentRequest request,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (validatorService.validateFloat(request.getMax_percent())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Tiêu chuẩn phần trăm nguyên liệu tối đa không hợp lệ!"));
            }
            materialStandardPercentService.createMaterialStandardPercent(request, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Tạo tiêu chuẩn phần trăm nguyên liệu thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/materialstandardpercents/{msp_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> updateMaterialStandardPercent(@PathVariable("msp_id") int msp_id,
            @RequestBody MaterialStandardPercentRequest request,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (validatorService.validateFloat(request.getMax_percent())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Tiêu chuẩn phần trăm nguyên liệu tối đa không hợp lệ!"));
            }
            materialStandardPercentService.updateMaterialStandardPercent(msp_id, request, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Cập nhật tiêu chuẩn phần trăm nguyên liệu thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/materialstandardpercents/{msp_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> deleteMaterialStandardPercent(@PathVariable("msp_id") int msp_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            materialStandardPercentService.deleteMaterialStandardPercent(msp_id, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Xóa tiêu chuẩn phần trăm nguyên liệu thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
