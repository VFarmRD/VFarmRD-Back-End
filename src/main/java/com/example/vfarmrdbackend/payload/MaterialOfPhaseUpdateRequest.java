package com.example.vfarmrdbackend.payload;

public class MaterialOfPhaseUpdateRequest {
    private int mop_id;
    private String material_id;
    private float material_cost;
    private int material_weight;
    private float material_percent;
    private String delivered_duty_paid;

    public MaterialOfPhaseUpdateRequest() {
    }

    public MaterialOfPhaseUpdateRequest(int mop_id, String material_id, float material_cost, int material_weight,
            float material_percent, String delivered_duty_paid) {
        this.mop_id = mop_id;
        this.material_id = material_id;
        this.material_cost = material_cost;
        this.material_weight = material_weight;
        this.material_percent = material_percent;
        this.delivered_duty_paid = delivered_duty_paid;
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
