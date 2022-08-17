package com.example.vfarmrdbackend.controller.phase;

import java.util.List;

import com.example.vfarmrdbackend.model.phase.Phase;
import com.example.vfarmrdbackend.payload.phase.PhaseUpdateRequest;
import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.phase.PhaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Phase", description = "The Phase's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class PhaseController {
    @Autowired
    PhaseService phaseService;

    @GetMapping("/phases")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getAllPhaseByFormula_id(@RequestParam("formula_id") int formula_id) {
        try {
            List<Phase> listPhases = phaseService.getAllPhaseByFormula_id(formula_id);
            if (listPhases != null) {
                return ResponseEntity.status(HttpStatus.OK).body(listPhases);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found any Phase!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/phases/{phase_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getPhaseByPhase_id(@PathVariable("phase_id") int phase_id) {
        try {
            Phase phase = phaseService.getPhaseByPhase_id(phase_id);
            if (phase != null) {
                return ResponseEntity.status(HttpStatus.OK).body(phase);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found Phase!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/phases/update")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> updatePhase(@RequestBody PhaseUpdateRequest phaseUpdateRequest,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (phaseService.updatePhase(phaseUpdateRequest, jwt)) {
                return ResponseEntity.status(HttpStatus.OK).body("Update phase successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phase not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/phases/delete/{phase_id}")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> deletePhase(@PathVariable("phase_id") int phase_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (phaseService.deletePhase(phase_id, jwt)) {
                return ResponseEntity.status(HttpStatus.OK).body("Delete phase successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phase not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
