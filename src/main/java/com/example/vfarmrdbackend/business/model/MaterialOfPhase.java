package com.example.vfarmrdbackend.business.model;

import javax.persistence.*;

@Entity
@Table(name = "materialofphase")
public class MaterialOfPhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mop_id;
    private int phase_id;
    private String material_id;
    private float material_percent;
    private String delivered_duty_paid;

    public MaterialOfPhase() {
    }

    public MaterialOfPhase(int mop_id, int phase_id, String material_id, float material_percent,
            String delivered_duty_paid) {
        this.mop_id = mop_id;
        this.phase_id = phase_id;
        this.material_id = material_id;
        this.material_percent = material_percent;
        this.delivered_duty_paid = delivered_duty_paid;
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

    public String getDelivered_duty_paid() {
        return delivered_duty_paid;
    }

    public void setDelivered_duty_paid(String delivered_duty_paid) {
        this.delivered_duty_paid = delivered_duty_paid;
    }

    
}
