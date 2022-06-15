package com.example.vfarmrdbackend.payload;

public class MaterialOfPhaseGetResponse {
    private String material_id;
    private float material_cost;
    private int material_weight;
    private float material_percent;

    public MaterialOfPhaseGetResponse() {
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public float getMaterial_cost() {
        return material_cost;
    }

    public void setMaterial_cost(float material_cost) {
        this.material_cost = material_cost;
    }

    public int getMaterial_weight() {
        return material_weight;
    }

    public void setMaterial_weight(int material_weight) {
        this.material_weight = material_weight;
    }

    public float getMaterial_percent() {
        return material_percent;
    }

    public void setMaterial_percent(float material_percent) {
        this.material_percent = material_percent;
    }

}
