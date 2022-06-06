package com.example.vfarmrdbackend.api.controller;

import java.util.List;

import com.example.vfarmrdbackend.business.model.MaterialOfPhase;
import com.example.vfarmrdbackend.data.repository.MaterialOfPhaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class MaterialOfPhaseController {
    @Autowired
    private MaterialOfPhaseRepository materialOfPhaseRepository;

    @GetMapping("/materialofphase")
    public ResponseEntity<List<MaterialOfPhase>> getAllMaterialOfPhases() {
        try {
            List<MaterialOfPhase> _listMaterialOfPhases = materialOfPhaseRepository.findAll();
            if (_listMaterialOfPhases.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_listMaterialOfPhases, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping("/phases/{id}")
    // @PreAuthorize("hasAuthority('staff')")
    // public ResponseEntity<?> getAllPhaseByFormula_id(@PathVariable("id") int id)
    // {
    // try {
    // List<Phase> _listPhases = repo.getAllPhaseByFormula_id(id);
    // if (_listPhases.isEmpty()) {
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }
    // return new ResponseEntity<>(_listPhases, HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(
    // "The server is down!",
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    // @PostMapping("/phases/create")
    // @PreAuthorize("hasAuthority('staff')")
    // public ResponseEntity<?> createPhase(@RequestBody Phase phase) {
    // try {
    // repo.save(phase);
    // return new ResponseEntity<>(
    // "Create new phase completed!",
    // HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(
    // "The server is down!",
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    // @PutMapping("/phases/update/{id}")
    // @PreAuthorize("hasAuthority('staff')")
    // public ResponseEntity<?> updatePhase(@PathVariable("id") int id, @RequestBody
    // Phase phase) {
    // Phase _phase = repo.getPhaseByPhase_id(id);
    // if (_phase != null) {

    // // repo.save(_role);
    // return new ResponseEntity<>("Update phase successfully!", HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
    // }

    // @DeleteMapping("/phases/delete/{id}")
    // @PreAuthorize("hasAuthority('staff')")
    // public ResponseEntity<?> deletePhase(@PathVariable("id") int id) {
    // try {
    // repo.deleteById(id);
    // return new ResponseEntity<>("Delete phase successfully!", HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(
    // "The server is down!",
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

}
