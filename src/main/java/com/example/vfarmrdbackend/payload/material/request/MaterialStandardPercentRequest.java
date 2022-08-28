package com.example.vfarmrdbackend.payload.material;

public class MaterialStandardPercentRequest {
    private String material_id;
    private float max_percent;

    public MaterialStandardPercentRequest() {
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public float getMax_percent() {
        return max_percent;
    }

    public void setMax_percent(float max_percent) {
        this.max_percent = max_percent;
    }

}
