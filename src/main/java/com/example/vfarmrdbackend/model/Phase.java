package com.example.vfarmrdbackend.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "phases")
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int phase_id;
    private int formula_id;
    private String formula_version;
    private float formula_cost;
    private String phase_description;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase_id", nullable = false, insertable = false, updatable = false)
    private List<MaterialOfPhase> materialofphase;

    public Phase() {
    }

    public Phase(int phase_id, int formula_id, String formula_version, float formula_cost, String phase_description,
            List<MaterialOfPhase> materialofphase) {
        this.phase_id = phase_id;
        this.formula_id = formula_id;
        this.formula_version = formula_version;
        this.formula_cost = formula_cost;
        this.phase_description = phase_description;
        this.materialofphase = materialofphase;
    }

    public List<MaterialOfPhase> getMaterialofphase() {
        return materialofphase;
    }

    public void setMaterialofphase(List<MaterialOfPhase> materialofphase) {
        this.materialofphase = materialofphase;
    }

    public int getPhase_id() {
        return phase_id;
    }

    public void setPhase_id(int phase_id) {
        this.phase_id = phase_id;
    }

    public int getFormula_id() {
        return formula_id;
    }

    public void setFormula_id(int formula_id) {
        this.formula_id = formula_id;
    }

    public String getFormula_version() {
        return formula_version;
    }

    public void setFormula_version(String formula_version) {
        this.formula_version = formula_version;
    }

    public float getFormula_cost() {
        return formula_cost;
    }

    public void setFormula_cost(float formula_cost) {
        this.formula_cost = formula_cost;
    }

    public String getPhase_description() {
        return phase_description;
    }

    public void setPhase_description(String phase_description) {
        this.phase_description = phase_description;
    }
}
