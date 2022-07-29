package com.example.vfarmrdbackend.payload.phase;

import com.example.vfarmrdbackend.payload.material.MaterialOfPhaseUpdateRequest;

import java.util.List;

public class PhaseUpdateRequest {
    private int phase_id;
    private int phase_index;
    private String phase_description;
    private List<MaterialOfPhaseUpdateRequest> materialOfPhaseUpdateRequest;
    private List<Integer> listTool_id;

    public PhaseUpdateRequest() {
    }

    public int getPhase_index() {
        return phase_index;
    }

    public void setPhase_index(int phase_index) {
        this.phase_index = phase_index;
    }

    public List<MaterialOfPhaseUpdateRequest> getMaterialOfPhaseUpdateRequest() {
        return materialOfPhaseUpdateRequest;
    }

    public void setMaterialOfPhaseUpdateRequest(List<MaterialOfPhaseUpdateRequest> materialOfPhaseUpdateRequest) {
        this.materialOfPhaseUpdateRequest = materialOfPhaseUpdateRequest;
    }

    public int getPhase_id() {
        return phase_id;
    }

    public void setPhase_id(int phase_id) {
        this.phase_id = phase_id;
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
