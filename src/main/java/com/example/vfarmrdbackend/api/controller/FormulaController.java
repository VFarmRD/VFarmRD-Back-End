package com.example.vfarmrdbackend.api.controller;

import java.util.List;

import com.example.vfarmrdbackend.business.model.Formula;
import com.example.vfarmrdbackend.business.payload.FormulaRequest;
import com.example.vfarmrdbackend.business.service.FormulaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class FormulaController {
    @Autowired
    private FormulaService formulaService;

    @GetMapping("/formulas")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    private ResponseEntity<?> getAllFormulaByProduct_id(@RequestParam("product_id") int product_id) {
        List<Formula> _listFormulas = formulaService.getAllFormulaByProduct_id(product_id);
        if (_listFormulas != null) {
            return ResponseEntity.status(HttpStatus.OK).body(_listFormulas);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Can't found any formula!");
        }
    }

    @GetMapping("/formulas/{id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    private ResponseEntity<?> getFormulaByFormula_id(@PathVariable("id") int formula_id) {
        Formula _formula = formulaService.getFormulaByFormula_id(formula_id);
        if (_formula != null) {
            return ResponseEntity.status(HttpStatus.OK).body(_formula);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Formula not found!");
        }
    }

    @PostMapping("/formulas/create")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> createFormula(@RequestBody FormulaRequest formulaRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            formulaService.createFormula(formulaRequest, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Create new formula completed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PutMapping("/formulas/submit/{id}")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> submitFormula(@PathVariable("id") int id) {
        if (formulaService.setFormula_status(id, "pending")) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Submit formula successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Submit formula fail!");
        }
    }

    @PutMapping("/formulas/delete/{id}")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> deleteFormula(@PathVariable("id") int id) {
        if (formulaService.setFormula_status(id, "deleted")) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Delete formula successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Delete formula fail!");
        }
    }

    @PostMapping("/formulas/update")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> updateFormula(@RequestBody FormulaRequest formulaRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            formulaService.createAnotherFormula_version(formulaRequest, jwt, "update");
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Update Formula successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down");
        }
    }

    @PostMapping("/formulas/upgrade")
    @PreAuthorize("hasAuthority('staff')")
    private ResponseEntity<?> upgradeFormula(@RequestBody FormulaRequest formulaRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            formulaService.createAnotherFormula_version(formulaRequest, jwt, "upgrade");
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Upgrade Formula successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down");
        }
    }

    @PutMapping("/formulas/approve/{id}")
    @PreAuthorize("hasAuthority('manager')")
    private ResponseEntity<?> approveFormula(@PathVariable("id") int id) {
        if (formulaService.setFormula_status(id, "approved")) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Approve formula successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    "This formula haven't submit yet!");
        }
    }

    @PutMapping("/formulas/deny/{id}")
    @PreAuthorize("hasAuthority('manager')")
    private ResponseEntity<?> denyFormula(@PathVariable("id") int id) {
        if (formulaService.setFormula_status(id, "denied")) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Approve formula successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    "This formula haven't submit yet!");
        }
    }
}
