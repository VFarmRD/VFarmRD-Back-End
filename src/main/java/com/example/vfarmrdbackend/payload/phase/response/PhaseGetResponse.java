package com.example.vfarmrdbackend.payload.phase.response;

import com.example.vfarmrdbackend.payload.material.response.MaterialOfPhaseGetResponse;
import com.example.vfarmrdbackend.payload.tool.response.ToolInPhaseResponse;

import java.util.List;

public class PhaseGetResponse {
    private int phase_id;
    private String phase_name;
    private String phase_description;
    private List<MaterialOfPhaseGetResponse> materialOfPhaseGetResponse;
    private List<ToolInPhaseResponse> listToolInPhaseResponse;

    public PhaseGetResponse() {
    }

    public PhaseGetResponse(int phase_id, String phase_name, String phase_description,
            List<MaterialOfPhaseGetResponse> materialOfPhaseGetResponse,
            List<ToolInPhaseResponse> listToolInPhaseResponse) {
        this.phase_id = phase_id;
        this.phase_name = phase_name;
        this.phase_description = phase_description;
        this.materialOfPhaseGetResponse = materialOfPhaseGetResponse;
        this.listToolInPhaseResponse = listToolInPhaseResponse;
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
