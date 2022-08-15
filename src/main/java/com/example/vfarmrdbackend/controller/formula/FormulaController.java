package com.example.vfarmrdbackend.controller.formula;

import com.example.vfarmrdbackend.payload.formula.FormulaCreateRequest;
import com.example.vfarmrdbackend.payload.formula.FormulaUpdateRequest;
import com.example.vfarmrdbackend.payload.formula.FormulaUpgradeRequest;
import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.formula.FormulaService;

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
    FormulaService formulaService;

    @GetMapping("/formulas")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getAllFormulaByProject_id(@RequestParam("project_id") int project_id,
            @RequestParam(defaultValue = "", required = false) String formula_status,
            @RequestHeader("Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(formulaService.getAllFormulaByProject_id(project_id,
                    formula_status, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/formulas/users/{user_id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getAllFormulaByUser_idAndFormula_status(@PathVariable("user_id") int user_id,
            @RequestParam(defaultValue = "", required = false) String formula_status,
            @RequestHeader("Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(formulaService.getAllFormulaByUser_idAndFormula_status(user_id,
                            formula_status, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/formulas/{formula_id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getFormulaByFormula_id(@PathVariable("formula_id") int formula_id,
            @RequestHeader("Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(formulaService.getFormulaByFormula_id(formula_id, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/formulas")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> createFormula(@RequestBody FormulaCreateRequest formulaCreateRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    formulaService.createFormula(formulaCreateRequest, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/formulas/{formula_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> deleteFormula(@PathVariable("formula_id") int formula_id,
            @RequestHeader("Authorization") String jwt) {
        try {
            if (formulaService.setFormula_status(formula_id, "canceled", jwt)) {
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
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> updateFormula(
            @PathVariable("formula_id") int formula_id,
            @RequestBody FormulaUpdateRequest formulaUpdateRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            int id = formulaService.updateFormula(formula_id, formulaUpdateRequest, jwt);
            if (id != 0) {
                return ResponseEntity.status(HttpStatus.OK).body(id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/formulas/{formula_id}/upgrade")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> upgradeFormula(
            @PathVariable("formula_id") int formula_id,
            @RequestBody FormulaUpgradeRequest formulaUpgradeRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            int id = formulaService.upgradeFormula(formula_id, formulaUpgradeRequest, jwt);
            if (id != 0) {
                return ResponseEntity.status(HttpStatus.OK).body(id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/formulas/{formula_id}/status")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> changeFormula_status(@PathVariable("formula_id") int formula_id,
            @RequestParam("status") String status,
            @RequestHeader("Authorization") String jwt) {
        try {
            if (formulaService.setFormula_status(formula_id, status, jwt)) {
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

    @PutMapping("/formulas/{formula_id}/deny-with-reason")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> denyWithReason(@PathVariable("formula_id") int formula_id,
            @RequestBody String deny_reason,
            @RequestHeader("Authorization") String jwt) {
        try {
            if (formulaService.denyFormulaWithReason(formula_id, deny_reason, jwt)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new MessageResponse("Thành công", "Công thức đã bị từ chối!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        new MessageResponse("Lỗi", "Công thức này chưa được gửi!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/formulas/statistics")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getFormulaStatistics(
            @RequestParam(defaultValue = "none", required = false) String from_date,
            @RequestParam(defaultValue = "none", required = false) String to_date,
            @RequestParam(defaultValue = "0", required = false) int month,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestHeader("Authorization") String jwt) {
        try {
            if (!from_date.equals("none") && !to_date.equals("none")) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        formulaService.getFormulaStatisticsFromDateToDate(jwt, from_date, to_date));
            } else if (month != 0 && year != 0) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        formulaService.getFormulaStatisticsWithMonthAndYear(jwt, month, year));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(
                        formulaService.getFormulaStatisticsOfAllTime(jwt));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
