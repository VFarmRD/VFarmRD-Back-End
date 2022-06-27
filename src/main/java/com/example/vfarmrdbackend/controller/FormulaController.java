package com.example.vfarmrdbackend.controller;

import java.util.List;

import com.example.vfarmrdbackend.model.Formula;
import com.example.vfarmrdbackend.payload.FormulaUpgradeRequest;
import com.example.vfarmrdbackend.payload.MessageResponse;
import com.example.vfarmrdbackend.payload.FormulaCreateRequest;
import com.example.vfarmrdbackend.payload.FormulaGetResponse;
import com.example.vfarmrdbackend.payload.FormulaUpdateRequest;
import com.example.vfarmrdbackend.service.FormulaService;

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

@Tag(name = "Formula", description = "The Formula's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class FormulaController {
    @Autowired
    public FormulaService formulaService;

    @GetMapping("/formulas")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getAllFormulaByProduct_id(@RequestParam("product_id") String product_id,
            @RequestParam(defaultValue = "", required = false) String formula_status) {
        try {
            List<Formula> listFormulas = formulaService.getAllFormulaByProduct_id(product_id,
                    "%" + formula_status + "%");
            if (listFormulas != null) {
                return ResponseEntity.status(HttpStatus.OK).body(listFormulas);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Không tìm thấy công thức nào!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/formulas/{formula_id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getFormulaByFormula_id(@PathVariable("formula_id") int formula_id) {
        try {
            FormulaGetResponse formulaGetResponse = formulaService.getFormulaByFormula_id(formula_id);
            if (formulaGetResponse != null) {
                return ResponseEntity.status(HttpStatus.OK).body(formulaGetResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Công thức không tồn tại!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/formulas")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> createFormula(@RequestBody FormulaCreateRequest formulaCreateRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            formulaService.createFormula(formulaCreateRequest, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Công thức mới đã được tạo!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/formulas/{formula_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> deleteFormula(@PathVariable("formula_id") int formula_id) {
        try {
            if (formulaService.setFormula_status(formula_id, "canceled")) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new MessageResponse("Thành công", "Công thức đã bị xóa!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Công thức không tồn tại!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/formulas/{formula_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> updateFormula(
            @PathVariable("formula_id") int formula_id,
            @RequestBody FormulaUpdateRequest formulaUpdateRequest) {
        try {
            if (formulaService.updateFormula(formula_id, formulaUpdateRequest)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new MessageResponse("Thành công", "Công thức đã được cập nhật!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Công thức không tồn tại!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/formulas/{formula_id}/upgrade")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> upgradeFormula(
            @PathVariable("formula_id") int formula_id,
            @RequestBody FormulaUpgradeRequest formulaUpgradeRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            if (formulaService.upgradeFormula(formula_id, formulaUpgradeRequest, jwt)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new MessageResponse("Thành công", "Công thức đã được nâng cấp!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("Lỗi", "Công thức không tồn tại!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/formulas/{formula_id}/status")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> changeFormula_status(@PathVariable("formula_id") int formula_id,
            @RequestParam("status") String status) {
        try {
            if (formulaService.setFormula_status(formula_id, status)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new MessageResponse("Thành công", "Công thức đã được cập nhật trạng thái!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        new MessageResponse("Lỗi", "Công thức này chưa được gửi!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
