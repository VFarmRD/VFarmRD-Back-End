package com.example.vfarmrdbackend.payload;

import java.util.List;

public class PhaseGetResponse {
    private int phase_id;
    private String phase_description;
    private List<MaterialOfPhaseGetResponse> materialOfPhaseGetResponse;

    public PhaseGetResponse() {
    }

    public PhaseGetResponse(int phase_id, String phase_description,
            List<MaterialOfPhaseGetResponse> materialOfPhaseGetResponse) {
        this.phase_id = phase_id;
        this.phase_description = phase_description;
        this.materialOfPhaseGetResponse = materialOfPhaseGetResponse;
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

    public List<MaterialOfPhaseGetResponse> getMaterialOfPhaseGetResponse() {
        return materialOfPhaseGetResponse;
    }

    public void setMaterialOfPhaseGetResponse(List<MaterialOfPhaseGetResponse> materialOfPhaseGetResponse) {
        this.materialOfPhaseGetResponse = materialOfPhaseGetResponse;
    }

}
