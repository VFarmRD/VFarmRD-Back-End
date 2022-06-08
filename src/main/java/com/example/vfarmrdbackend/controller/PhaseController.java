package com.example.vfarmrdbackend.controller;

import java.util.List;

import com.example.vfarmrdbackend.model.Phase;
import com.example.vfarmrdbackend.service.PhaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Phase", description = "The Phase's API")
@RestController
@RequestMapping(path = "/api")
public class PhaseController {
    @Autowired
    PhaseService phaseService;

    @GetMapping("/phases")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> getAllPhaseByFormula_id(@RequestParam("id") int formula_id) {
        try {
            List<Phase> _listPhases = phaseService.getAllPhaseByFormula_id(formula_id);
            if (_listPhases != null) {
                return ResponseEntity.status(HttpStatus.OK).body(_listPhases);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found any Phase!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @GetMapping("/phases/{id}")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> getPhaseByPhase_id(@PathVariable("id") int phase_id) {
        try {
            Phase _phase = phaseService.getPhaseByPhase_id(phase_id);
            if (_phase != null) {
                return ResponseEntity.status(HttpStatus.OK).body(_phase);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Can't found Phase!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PostMapping("/phases/create")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> createPhase(@RequestBody Phase phase) {
        try {
            phaseService.createPhase(phase);
            return ResponseEntity.status(HttpStatus.OK).body("Create new phase completed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PutMapping("/phases/update")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> updatePhase(@RequestBody Phase phase) {
        try {
            if (phaseService.updatePhase(phase)) {
                return ResponseEntity.status(HttpStatus.OK).body("Update phase successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phase not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @DeleteMapping("/phases/delete/{id}")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> deletePhase(@PathVariable("id") int phase_id) {
        try {
            phaseService.deletePhase(phase_id);
            return ResponseEntity.status(HttpStatus.OK).body("Delete phase successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }
}
