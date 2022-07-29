package com.example.vfarmrdbackend.model.tool;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "toolcategories")
public class ToolCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int toolcategory_id;
    private String toolcategory_name;
    private String description;

    public ToolCategory(String toolcategory_name, String description) {
        this.toolcategory_name = toolcategory_name;
        this.description = description;
    }

    public ToolCategory() {
    }

    public int getToolcategory_id() {
        return toolcategory_id;
    }

    public void setToolcategory_id(int toolcategory_id) {
        this.toolcategory_id = toolcategory_id;
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
