package com.example.vfarmrdbackend.api.controller;

import java.util.List;

import com.example.vfarmrdbackend.business.model.MaterialOfPhase;
import com.example.vfarmrdbackend.business.service.MaterialOfPhaseService;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Material Of Phase", description = "The Material Of Phase's API")
@RestController
@RequestMapping(path = "/api")
public class MaterialOfPhaseController {
    @Autowired
    MaterialOfPhaseService materialOfPhaseService;

    @GetMapping("/materialofphase")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> getAllMOPwithPhase_id(@RequestParam("phase_id") int phase_id) {
        try {
            List<MaterialOfPhase> _listMaterialOfPhases = materialOfPhaseService.getAllMaterialOfPhase(phase_id);
            if (_listMaterialOfPhases != null) {
                return ResponseEntity.status(HttpStatus.OK).body(_listMaterialOfPhases);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found any material!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @GetMapping("/materialofphase/{id}")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> getTestByTest_id(@PathVariable("id") int mop_id) {
        try {
            MaterialOfPhase _materialOfPhase = materialOfPhaseService.getMaterialOfPhase(mop_id);
            if (_materialOfPhase != null) {
                return ResponseEntity.status(HttpStatus.OK).body(_materialOfPhase);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found material!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PostMapping("/materialofphase/create")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> createTest(@RequestBody MaterialOfPhase materialOfPhase) {
        try {
            materialOfPhaseService.createMaterialOfPhase(materialOfPhase);
            return ResponseEntity.status(HttpStatus.OK).body("Create new Material Of Phase completed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PutMapping("/materialofphase/update")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> updateMaterialOfPhase(@RequestBody MaterialOfPhase materialOfPhase) {
        try {
            if (materialOfPhaseService.updateMaterialOfPhase(materialOfPhase)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        "Update Material Of Phase successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Material Of Phase not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @DeleteMapping("/materialofphase/delete/{id}")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> deleteMaterialOfPhase(@PathVariable("id") int mop_id) {
        try {
            if (materialOfPhaseService.deleteMaterialOfPhase(mop_id)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        "Delete Material Of Phase successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Material Of Phase not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

}
