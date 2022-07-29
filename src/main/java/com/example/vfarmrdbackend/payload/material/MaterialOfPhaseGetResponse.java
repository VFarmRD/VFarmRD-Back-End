package com.example.vfarmrdbackend.payload.material;

public class MaterialOfPhaseGetResponse {
    private int mop_id;
    private String material_id;
    private float material_cost;
    private float material_weight;
    private float material_percent;
    private String material_description;

    public MaterialOfPhaseGetResponse() {
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

    public float getMaterial_cost() {
        return material_cost;
    }

    public void setMaterial_cost(float material_cost) {
        this.material_cost = material_cost;
    }

    public float getMaterial_percent() {
        return material_percent;
    }

    public void setMaterial_percent(float material_percent) {
        this.material_percent = material_percent;
    }

    public String getMaterial_description() {
        return material_description;
    }

    public void setMaterial_description(String material_description) {
        this.material_description = material_description;
    }

    public float getMaterial_weight() {
        return material_weight;
    }

    public void setMaterial_weight(float material_weight) {
        this.material_weight = material_weight;
    }

}
