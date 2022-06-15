package com.example.vfarmrdbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "materialofphase")
public class MaterialOfPhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mop_id;
    private int phase_id;
    private String material_id;
    private float material_cost;
    private int material_weight;
    private float material_percent;

    public MaterialOfPhase() {
    }

    public int getMaterial_weight() {
        return material_weight;
    }

    public void setMaterial_weight(int material_weight) {
        this.material_weight = material_weight;
    }

    public float getMaterial_cost() {
        return material_cost;
    }

    public void setMaterial_cost(float material_cost) {
        this.material_cost = material_cost;
    }

    public int getMop_id() {
        return mop_id;
    }

    public void setMop_id(int mop_id) {
        this.mop_id = mop_id;
    }

    public int getPhase_id() {
        return phase_id;
    }

    public void setPhase_id(int phase_id) {
        this.phase_id = phase_id;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public float getMaterial_percent() {
        return material_percent;
    }

    public void setMaterial_percent(float material_percent) {
        this.material_percent = material_percent;
    }

}
