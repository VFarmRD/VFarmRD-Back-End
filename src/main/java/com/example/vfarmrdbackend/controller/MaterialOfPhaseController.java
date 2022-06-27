package com.example.vfarmrdbackend.controller;

import java.util.List;

import com.example.vfarmrdbackend.model.MaterialOfPhase;
import com.example.vfarmrdbackend.payload.MaterialOfPhaseUpdateRequest;
import com.example.vfarmrdbackend.service.MaterialOfPhaseService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Material Of Phase", description = "The Material Of Phase's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class MaterialOfPhaseController {
    @Autowired
    MaterialOfPhaseService materialOfPhaseService;

    @GetMapping("/materialofphase")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getAllMOPwithPhase_id(@RequestParam("phase_id") int phase_id) {
        try {
            List<MaterialOfPhase>listMaterialOfPhases = materialOfPhaseService.getAllMaterialOfPhase(phase_id);
            if (listMaterialOfPhases != null) {
                return ResponseEntity.status(HttpStatus.OK).body(listMaterialOfPhases);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found any material!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    e.getMessage());
        }
    }

    @GetMapping("/materialofphase/{mop_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> getMaterialOfPhase(@PathVariable("mop_id") int mop_id) {
        try {
            MaterialOfPhase materialOfPhase = materialOfPhaseService.getMaterialOfPhase(mop_id);
            if (materialOfPhase != null) {
                return ResponseEntity.status(HttpStatus.OK).body(materialOfPhase);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found material!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    e.getMessage());
        }
    }

    @PutMapping("/materialofphase/update")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> updateMaterialOfPhase(
            @RequestBody MaterialOfPhaseUpdateRequest materialOfPhaseUpdateRequest) {
        try {
            if (materialOfPhaseService.updateMaterialOfPhase(materialOfPhaseUpdateRequest)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        "Update Material Of Phase successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Material Of Phase not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    e.getMessage());
        }
    }

    @DeleteMapping("/materialofphase/delete/{mop_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> deleteMaterialOfPhase(@PathVariable("mop_id") int mop_id) {
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
                    e.getMessage());
        }
    }

}
