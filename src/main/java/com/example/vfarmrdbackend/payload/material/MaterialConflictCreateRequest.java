package com.example.vfarmrdbackend.payload.material;

public class MaterialConflictCreateRequest {
    private String first_material_id;
    private String second_material_id;
    private String description;

    public MaterialConflictCreateRequest() {
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
