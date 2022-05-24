package com.example.vfarmrdbackend.controllers;

import java.util.Date;
import java.util.List;

import com.example.vfarmrdbackend.models.Formula;
import com.example.vfarmrdbackend.models.Product;
import com.example.vfarmrdbackend.repositories.FormulaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class FormulaController {
    @Autowired
    private FormulaRepository repo;

    Date date = new Date();

    @GetMapping("/formulas")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getAllFormulaByProduct_id(@RequestBody Product product) {
        List<Formula> _listFormulas = repo.getAllFormulaByProduct_id(product.getProduct_id());
        if (_listFormulas != null) {
            return new ResponseEntity<>(_listFormulas, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(
                    "Can't found any formula!",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/formulas/{id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getFormulaByFormula_id(@PathVariable("id") int id) {
        Formula _formula = repo.getFormulaByFormula_id(id);
        if (_formula != null) {
            return new ResponseEntity<>(_formula, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(
                    "Formula not found!",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/formulas/create")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> createFormula(@RequestBody Formula formula) {
        try {
            formula.setFormula_version("1.0");
            formula.setFormula_status("on progress");
            formula.setCreated_time(date);
            repo.save(formula);
            return new ResponseEntity<>(
                    "Create new formula completed!",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/formulas/submit/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> submitFormula(@PathVariable("id") int id, @RequestBody Formula formula) {
        Formula _formula = repo.getFormulaByFormula_id(id);
        if (_formula != null) {
            _formula.setFormula_status("pending");
            _formula.setModified_time(date);
            repo.save(_formula);
            return new ResponseEntity<>("Submit formula successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/formulas/delete/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> deleteFormula(@PathVariable("id") int id, @RequestBody Formula formula) {
        Formula _formula = repo.getFormulaByFormula_id(id);
        if (_formula != null) {
            _formula.setFormula_status("deleted");
            _formula.setModified_time(date);
            repo.save(_formula);
            return new ResponseEntity<>("Delete formula successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/formulas/update/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> updateFormula(@PathVariable("id") int id, @RequestBody Formula formula) {
        Formula _formula = repo.getFormulaByFormula_id(id);
        if (_formula != null) {
            String formula_pre_version = _formula.getFormula_version();
            String[] splitString = formula_pre_version.split(".");
            String formula_now_version = "1" + Integer.parseInt(splitString[1] + 1);
            _formula.setFormula_version(formula_now_version);
            _formula.setFormula_name(formula.getFormula_name());
            _formula.setFormula_cost(formula.getFormula_cost());
            _formula.setModified_time(date);
            repo.save(_formula);
            return new ResponseEntity<>("Update formula successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/formulas/upgrade/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public ResponseEntity<?> upgradeFormula(@PathVariable("id") int id, @RequestBody Formula formula) {
        Formula _formula = repo.getFormulaByFormula_id(id);
        if (_formula != null) {
            String formula_pre_version = _formula.getFormula_version();
            String[] splitString = formula_pre_version.split(".");
            String formula_now_version = Integer.parseInt(splitString[0] + 1) + ".0";
            _formula.setFormula_version(formula_now_version);
            _formula.setFormula_name(formula.getFormula_name());
            _formula.setFormula_cost(formula.getFormula_cost());
            _formula.setModified_time(date);
            repo.save(_formula);
            return new ResponseEntity<>("Upgrade formula successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Approve Formula
    @PutMapping("/formulas/approve/{id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> approveFormula(@PathVariable("id") int id, @RequestBody Formula formula) {
        Formula _formula = repo.getFormulaByFormula_id(id);
        if (_formula != null) {
            _formula.setFormula_status("approve");
            _formula.setModified_time(date);
            repo.save(_formula);
            return new ResponseEntity<>("Approve formula successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Deny Formula
    @PutMapping("/formulas/deny/{id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> denyFormula(@PathVariable("id") int id, @RequestBody Formula formula) {
        Formula _formula = repo.getFormulaByFormula_id(id);
        if (_formula != null) {
            _formula.setFormula_status("deny");
            _formula.setModified_time(date);
            repo.save(_formula);
            return new ResponseEntity<>("Deny formula successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
