package com.example.vfarmrdbackend.payload.tool;

public class ToolCategoryRequest {
    private String toolcategory_name;
    private String description;

    public ToolCategoryRequest() {
    }

    public String getToolcategory_name() {
        return toolcategory_name;
    }

    public void setToolcategory_name(String toolcategory_name) {
        this.toolcategory_name = toolcategory_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
