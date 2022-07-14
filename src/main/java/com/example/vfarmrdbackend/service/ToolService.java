package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Tool;
import com.example.vfarmrdbackend.payload.ToolRequest;
import com.example.vfarmrdbackend.repository.ToolRepository;

@Service
public class ToolService {

    @Autowired
    ToolRepository toolRepository;

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public Tool getTool(int tool_id) {
        return toolRepository.getToolByTool_id(tool_id);
    }

    public void createTool(ToolRequest request, String jwt) {
        Tool newTool = new Tool(
                request.getTool_name(),
                request.getToolcategory_id(),
                request.getDescription());
        toolRepository.save(newTool);
    }

    public void updateTool(int tool_id, ToolRequest request, String jwt) {
        Tool updateTool = toolRepository.getToolByTool_id(tool_id);
        updateTool.setTool_name(request.getTool_name());
        updateTool.setToolcategory_id(request.getToolcategory_id());
        updateTool.setDescription(request.getDescription());
        toolRepository.save(updateTool);
    }

    public void deleteTool(int tool_id, String jwt) {
        toolRepository.delete(toolRepository.getToolByTool_id(tool_id));
    }
}
