package com.example.vfarmrdbackend.controller;

import java.util.List;

import com.example.vfarmrdbackend.model.Formula;
import com.example.vfarmrdbackend.payload.FormulaRequest;
import com.example.vfarmrdbackend.service.FormulaService;

import io.swagger.v3.oas.annotations.tags.Tag;

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

@Tag(name = "Formula", description = "The Formula's API")
@RestController
@RequestMapping(path = "/api")
public class FormulaController {
    @Autowired
    public FormulaService formulaService;

    @GetMapping("/formulas")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getAllFormulaByProduct_id(@RequestParam("product_id") String product_id) {
        List<Formula> _listFormulas = formulaService.getAllFormulaByProduct_id(product_id);
        if (_listFormulas != null) {
            return ResponseEntity.status(HttpStatus.OK).body(_listFormulas);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Can't found any formula!");
        }
    }

    @GetMapping("/formulas/{formula_id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getFormulaByFormula_id(@PathVariable("formula_id") int formula_id) {
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
    public ResponseEntity<?> createFormula(@RequestBody FormulaRequest formulaRequest,
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

    @PutMapping("/formulas/submit/{formula_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> submitFormula(@PathVariable("formula_id") int formula_id) {
        try {
            formulaService.setFormula_status(formula_id, "pending");
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Submit formula successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Submit formula fail!");
        }
    }

    @PutMapping("/formulas/delete/{formula_id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> deleteFormula(@PathVariable("formula_id") int formula_id) {
        try {
            formulaService.setFormula_status(formula_id, "deleted");
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Delete formula successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Delete formula fail!");
        }
    }

    @PostMapping("/formulas/update")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> updateFormula(@RequestBody FormulaRequest formulaRequest,
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
    public ResponseEntity<?> upgradeFormula(@RequestBody FormulaRequest formulaRequest,
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

    @PutMapping("/formulas/approve/{formula_id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> approveFormula(@PathVariable("formula_id") int formula_id) {
        try {
            if (formulaService.setFormula_status(formula_id, "approved")) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        "Approve formula successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        "This formula haven't submit yet!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Approve formula fail!");
        }
    }

    @PutMapping("/formulas/deny/{formula_id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> denyFormula(@PathVariable("formula_id") int formula_id) {
        try {
            if (formulaService.setFormula_status(formula_id, "denied")) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        "Deny formula successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        "This formula haven't submit yet!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Deny formula fail!");
        }
    }
}
