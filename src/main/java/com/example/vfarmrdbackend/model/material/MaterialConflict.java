package com.example.vfarmrdbackend.model.material;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "materialconflicts")
public class MaterialConflict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int materialconflict_id;
    private String first_material_id;
    private String second_material_id;
    private String description;

    public MaterialConflict() {
    }

    public MaterialConflict(String first_material_id, String second_material_id, String description) {
        this.first_material_id = first_material_id;
        this.second_material_id = second_material_id;
        this.description = description;
    }

    public int getMaterialconflict_id() {
        return materialconflict_id;
    }

    public void setMaterialconflict_id(int materialconflict_id) {
        this.materialconflict_id = materialconflict_id;
    }

    public String getFirst_material_id() {
        return first_material_id;
    }

    public void setFirst_material_id(String first_material_id) {
        this.first_material_id = first_material_id;
    }

    public String getSecond_material_id() {
        return second_material_id;
    }

    public void setSecond_material_id(String second_material_id) {
        this.second_material_id = second_material_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
