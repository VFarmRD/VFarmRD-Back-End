package com.example.vfarmrdbackend.controllers;

import java.util.List;

import com.example.vfarmrdbackend.models.MaterialOfPhase;
import com.example.vfarmrdbackend.repositories.MaterialOfPhaseRepository;

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
    private MaterialOfPhaseRepository repo;

    @GetMapping("/materialofphase")
    public ResponseEntity<List<MaterialOfPhase>> getAllFiles() {
        try {
            List<MaterialOfPhase> _listMaterialOfPhases = repo.findAll();
            if (_listMaterialOfPhases.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_listMaterialOfPhases, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
