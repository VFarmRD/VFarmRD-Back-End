package com.example.vfarmrdbackend.payload.phase;

import com.example.vfarmrdbackend.payload.material.MaterialOfPhaseCreateRequest;

import java.util.List;

public class PhaseCreateRequest {
    private int phase_index;
    private String phase_description;
    private List<MaterialOfPhaseCreateRequest> materialOfPhaseCreateRequest;
    private List<Integer> listTool_id;

    public PhaseCreateRequest() {
    }

    public int getPhase_index() {
        return phase_index;
    }

    public void setPhase_index(int phase_index) {
        this.phase_index = phase_index;
    }

    public List<MaterialOfPhaseCreateRequest> getMaterialOfPhaseCreateRequest() {
        return materialOfPhaseCreateRequest;
    }

    public void setMaterialOfPhaseCreateRequest(List<MaterialOfPhaseCreateRequest> materialOfPhaseCreateRequest) {
        this.materialOfPhaseCreateRequest = materialOfPhaseCreateRequest;
    }

    public String getPhase_description() {
        return phase_description;
    }

    public void setPhase_description(String phase_description) {
        this.phase_description = phase_description;
    }

    public List<Integer> getListTool_id() {
        return listTool_id;
    }

    public void setListTool_id(List<Integer> listTool_id) {
        this.listTool_id = listTool_id;
    }

}
