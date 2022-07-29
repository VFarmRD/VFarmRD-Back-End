package com.example.vfarmrdbackend.model.tool;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tools")
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tool_id;
    private String tool_name;
    private int toolcategory_id;
    private String description;
    private String parameter;
    private String unit;

    public Tool() {
    }

    public Tool(String tool_name, int toolcategory_id, String description, String parameter, String unit) {
        this.tool_name = tool_name;
        this.toolcategory_id = toolcategory_id;
        this.description = description;
        this.parameter = parameter;
        this.unit = unit;
    }

    public int getTool_id() {
        return tool_id;
    }

    public void setTool_id(int tool_id) {
        this.tool_id = tool_id;
    }

    public String getTool_name() {
        return tool_name;
    }

    public void setTool_name(String tool_name) {
        this.tool_name = tool_name;
    }

    public int getToolcategory_id() {
        return toolcategory_id;
    }

    public void setToolcategory_id(int toolcategory_id) {
        this.toolcategory_id = toolcategory_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
