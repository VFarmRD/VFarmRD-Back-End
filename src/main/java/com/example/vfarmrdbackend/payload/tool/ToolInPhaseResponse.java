package com.example.vfarmrdbackend.payload.tool;

public class ToolInPhaseResponse {
    private int toolinphase_id;
    private int phase_id;
    private ToolResponse toolResponse;

    public ToolInPhaseResponse() {
    }

    public ToolInPhaseResponse(int toolinphase_id, int phase_id, ToolResponse toolResponse) {
        this.toolinphase_id = toolinphase_id;
        this.phase_id = phase_id;
        this.toolResponse = toolResponse;
    }

    public int getToolinphase_id() {
        return toolinphase_id;
    }

    public void setToolinphase_id(int toolinphase_id) {
        this.toolinphase_id = toolinphase_id;
    }

    public int getPhase_id() {
        return phase_id;
    }

    public void setPhase_id(int phase_id) {
        this.phase_id = phase_id;
    }

    public ToolResponse getToolResponse() {
        return toolResponse;
    }

    public void setToolResponse(ToolResponse toolResponse) {
        this.toolResponse = toolResponse;
    }

}
