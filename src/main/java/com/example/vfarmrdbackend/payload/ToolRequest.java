package com.example.vfarmrdbackend.payload;

public class ToolRequest {
    private String tool_name;
    private int toolcategory_id;
    private String description;

    public ToolRequest() {
    }

    public ToolRequest(String tool_name, int toolcategory_id, String description) {
        this.tool_name = tool_name;
        this.toolcategory_id = toolcategory_id;
        this.description = description;
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

}
