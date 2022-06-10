package com.example.vfarmrdbackend.payload;

import java.util.List;

public class PhaseUpdateRequest {
    private int phase_id;
    private String phase_description;
    private List<MaterialOfPhaseCreateRequest> materialOfPhaseCreateRequest;

    public PhaseUpdateRequest() {
    }

    public PhaseUpdateRequest(int phase_id, String phase_description,
            List<MaterialOfPhaseCreateRequest> materialOfPhaseCreateRequest) {
        this.phase_id = phase_id;
        this.phase_description = phase_description;
        this.materialOfPhaseCreateRequest = materialOfPhaseCreateRequest;
    }

    public List<MaterialOfPhaseCreateRequest> getMaterialOfPhaseCreateRequest() {
        return materialOfPhaseCreateRequest;
    }

    public void setMaterialOfPhaseCreateRequest(List<MaterialOfPhaseCreateRequest> materialOfPhaseCreateRequest) {
        this.materialOfPhaseCreateRequest = materialOfPhaseCreateRequest;
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

}
