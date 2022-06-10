package com.example.vfarmrdbackend.model;

import javax.persistence.*;

@Entity
@Table(name = "phases")
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int phase_id;
    private int formula_id;
    private String phase_description;

    public Phase() {
    }

    public Phase(int phase_id, int formula_id, String phase_description) {
        this.phase_id = phase_id;
        this.formula_id = formula_id;
        this.phase_description = phase_description;
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

    public String getPhase_description() {
        return phase_description;
    }

    public void setPhase_description(String phase_description) {
        this.phase_description = phase_description;
    }
}
