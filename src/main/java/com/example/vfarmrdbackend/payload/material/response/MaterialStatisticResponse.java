package com.example.vfarmrdbackend.payload.material;

import java.util.List;

public class MaterialStatisticResponse {
    private int total_material_used;
    private String most_material_id_used;
    private int most_material_id_used_time;
    private List<String> top_10_materials_most_used_by_percent;
    private List<Float> top_10_materials_most_used_by_percent_time;
    private List<String> top_10_materials_most_used_by_weight;
    private List<Float> top_10_materials_most_used_by_weight_time;

    public MaterialStatisticResponse() {
    }

    public MaterialStatisticResponse(int total_material_used, String most_material_id_used,
            int most_material_id_used_time, List<String> top_10_materials_most_used_by_percent,
            List<Float> top_10_materials_most_used_by_percent_time, List<String> top_10_materials_most_used_by_weight,
            List<Float> top_10_materials_most_used_by_weight_time) {
        this.total_material_used = total_material_used;
        this.most_material_id_used = most_material_id_used;
        this.most_material_id_used_time = most_material_id_used_time;
        this.top_10_materials_most_used_by_percent = top_10_materials_most_used_by_percent;
        this.top_10_materials_most_used_by_percent_time = top_10_materials_most_used_by_percent_time;
        this.top_10_materials_most_used_by_weight = top_10_materials_most_used_by_weight;
        this.top_10_materials_most_used_by_weight_time = top_10_materials_most_used_by_weight_time;
    }

    public int getTotal_material_used() {
        return total_material_used;
    }

    public void setTotal_material_used(int total_material_used) {
        this.total_material_used = total_material_used;
    }

    public String getMost_material_id_used() {
        return most_material_id_used;
    }

    public void setMost_material_id_used(String most_material_id_used) {
        this.most_material_id_used = most_material_id_used;
    }

    public int getMost_material_id_used_time() {
        return most_material_id_used_time;
    }

    public void setMost_material_id_used_time(int most_material_id_used_time) {
        this.most_material_id_used_time = most_material_id_used_time;
    }

    public List<String> getTop_10_materials_most_used_by_percent() {
        return top_10_materials_most_used_by_percent;
    }

    public void setTop_10_materials_most_used_by_percent(List<String> top_10_materials_most_used_by_percent) {
        this.top_10_materials_most_used_by_percent = top_10_materials_most_used_by_percent;
    }

    public List<Float> getTop_10_materials_most_used_by_percent_time() {
        return top_10_materials_most_used_by_percent_time;
    }

    public void setTop_10_materials_most_used_by_percent_time(List<Float> top_10_materials_most_used_by_percent_time) {
        this.top_10_materials_most_used_by_percent_time = top_10_materials_most_used_by_percent_time;
    }

    public List<String> getTop_10_materials_most_used_by_weight() {
        return top_10_materials_most_used_by_weight;
    }

    public void setTop_10_materials_most_used_by_weight(List<String> top_10_materials_most_used_by_weight) {
        this.top_10_materials_most_used_by_weight = top_10_materials_most_used_by_weight;
    }

    public List<Float> getTop_10_materials_most_used_by_weight_time() {
        return top_10_materials_most_used_by_weight_time;
    }

    public void setTop_10_materials_most_used_by_weight_time(List<Float> top_10_materials_most_used_by_weight_time) {
        this.top_10_materials_most_used_by_weight_time = top_10_materials_most_used_by_weight_time;
    }

}
