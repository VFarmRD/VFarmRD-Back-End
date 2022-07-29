package com.example.vfarmrdbackend.payload.phase;

import com.example.vfarmrdbackend.payload.material.MaterialOfPhaseGetResponse;
import com.example.vfarmrdbackend.payload.tool.ToolInPhaseResponse;

import java.util.List;

public class PhaseGetResponse {
    private int phase_id;
    private String phase_name;
    private String phase_description;
    private List<MaterialOfPhaseGetResponse> materialOfPhaseGetResponse;
    private List<ToolInPhaseResponse> listToolInPhaseResponse;

    public PhaseGetResponse() {
    }

    public String getPhase_name() {
        return phase_name;
    }

    public void setPhase_name(String phase_name) {
        this.phase_name = phase_name;
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

    public List<ToolInPhaseResponse> getListToolInPhaseResponse() {
        return listToolInPhaseResponse;
    }

    public void setListToolInPhaseResponse(List<ToolInPhaseResponse> listToolInPhaseResponse) {
        this.listToolInPhaseResponse = listToolInPhaseResponse;
    }

}
