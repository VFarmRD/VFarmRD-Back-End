package com.example.vfarmrdbackend.model.material;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "materialstandardpercent")
public class MaterialStandardPercent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int msp_id;
    private String material_id;
    private float max_percent;

    public MaterialStandardPercent() {
    }

    public int getMsp_id() {
        return msp_id;
    }

    public void setMsp_id(int msp_id) {
        this.msp_id = msp_id;
    }

    public float getMax_percent() {
        return max_percent;
    }

    public void setMax_percent(float max_percent) {
        this.max_percent = max_percent;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

}
