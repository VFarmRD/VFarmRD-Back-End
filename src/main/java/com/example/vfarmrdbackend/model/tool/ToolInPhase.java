package com.example.vfarmrdbackend.model.tool;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "toolinphases")
public class ToolInPhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int toolinphase_id;
    private int tool_id;
    private int phase_id;

    public ToolInPhase() {
    }

    public ToolInPhase(int tool_id, int phase_id) {
        this.tool_id = tool_id;
        this.phase_id = phase_id;
    }

    public int getToolinphase_id() {
        return toolinphase_id;
    }

    public void setToolinphase_id(int toolinphase_id) {
        this.toolinphase_id = toolinphase_id;
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
