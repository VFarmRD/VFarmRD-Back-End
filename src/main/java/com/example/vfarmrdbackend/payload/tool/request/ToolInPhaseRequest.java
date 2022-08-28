package com.example.vfarmrdbackend.payload.tool;

public class ToolInPhaseRequest {
    private int tool_id;
    private int phase_id;

    public ToolInPhaseRequest() {
    }

    public ToolInPhaseRequest(int tool_id, int phase_id) {
        this.tool_id = tool_id;
        this.phase_id = phase_id;
    }

    public int getTool_id() {
        return tool_id;
    }

    public void setTool_id(int tool_id) {
        this.tool_id = tool_id;
    }

    public int getPhase_id() {
        return phase_id;
    }

    public void setPhase_id(int phase_id) {
        this.phase_id = phase_id;
    }

}
