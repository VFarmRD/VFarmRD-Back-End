package com.example.vfarmrdbackend.controller.material;

import java.util.List;

import com.example.vfarmrdbackend.payload.material.request.MaterialOfPhaseUpdateRequest;
import com.example.vfarmrdbackend.payload.others.response.MessageResponse;
import com.example.vfarmrdbackend.service.material.MaterialOfPhaseService;
import com.example.vfarmrdbackend.service.others.ValidatorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

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
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Material Of Phase", description = "The Material Of Phase's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class MaterialOfPhaseController {
    @Autowired
    MaterialOfPhaseService materialOfPhaseService;

    @Autowired
    ValidatorService validatorService;

    @GetMapping("/materialofphase")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getAllMOPwithPhase_id(@RequestParam("phase_id") int phase_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(materialOfPhaseService.getAllMaterialOfPhase(phase_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/materialofphase/{mop_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getMaterialOfPhase(@PathVariable("mop_id") int mop_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(materialOfPhaseService.getMaterialOfPhase(mop_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/materialofphase/update")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> updateMaterialOfPhase(
            @RequestBody MaterialOfPhaseUpdateRequest materialOfPhaseUpdateRequest,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (validatorService.validateFloat(materialOfPhaseUpdateRequest.getMaterial_cost())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Giá nguyên liệu không hợp lệ!"));
            }
            if (validatorService.validateFloat(materialOfPhaseUpdateRequest.getMaterial_percent())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Phần trăm nguyên liệu không hợp lệ!"));
            }
            if (validatorService.validateFloat(materialOfPhaseUpdateRequest.getMaterial_weight())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Khối lượng nguyên liệu không hợp lệ!"));
            }
            materialOfPhaseService.updateMaterialOfPhase(materialOfPhaseUpdateRequest, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Cập nhật nguyên liệu trong pha thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/materialofphase/delete/{mop_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> deleteMaterialOfPhase(@PathVariable("mop_id") int mop_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            materialOfPhaseService.deleteMaterialOfPhase(mop_id, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Xóa nguyên liệu trong pha thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/materialofphase/projects/{project_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getAllMaterial_idWithProject_id(@PathVariable("project_id") int project_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(materialOfPhaseService.getAllMaterial_idWithProject_id(project_id, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/materials/{material_id}/upload")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> uploadFileForMaterial(@RequestParam("file") List<MultipartFile> listFile,
            @PathVariable("material_id") String material_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    materialOfPhaseService.uploadFileForMaterial(jwt, listFile, material_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống gặp sự cố!"));
        }
    }

    @GetMapping("/materials/statistics")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getMaterialStatistics(
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    materialOfPhaseService.getMaterialStatisticsOfAllTime(jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/materials/{material_id}/get-file")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getFileByMaterial_id(@PathVariable("material_id") String material_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    materialOfPhaseService.getFileByMaterial_id(material_id, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống gặp sự cố!"));
        }
    }

}
