package com.example.vfarmrdbackend.payload;

import java.util.List;

public class PhaseCreateRequest {
    private String phase_description;
    private List<MaterialOfPhaseCreateRequest> materialOfPhaseCreateRequest;

    public PhaseCreateRequest() {
    }

    public PhaseCreateRequest(String phase_description,
            List<MaterialOfPhaseCreateRequest> materialOfPhaseCreateRequest) {
        this.phase_description = phase_description;
        this.materialOfPhaseCreateRequest = materialOfPhaseCreateRequest;
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

}
