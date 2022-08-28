package com.example.vfarmrdbackend.service.tool;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.tool.Tool;
import com.example.vfarmrdbackend.payload.tool.request.ToolRequest;
import com.example.vfarmrdbackend.payload.tool.response.ToolResponse;
import com.example.vfarmrdbackend.repository.tool.ToolRepository;

@Service
public class ToolService {

    @Autowired
    ToolRepository toolRepository;

    @Autowired
    ToolCategoryService toolCategoryService;

    public List<ToolResponse> getAllTools() {
        try {
            List<Tool> listTools = toolRepository.findAll();
            List<ToolResponse> listResponse = new ArrayList<>();
            for (int i = 0; i < listTools.size(); i++) {
                listResponse.add(new ToolResponse(
                        listTools.get(i).getTool_id(),
                        listTools.get(i).getTool_name(),
                        listTools.get(i).getToolcategory_id(),
                        toolCategoryService.getToolCategory(listTools.get(i).getToolcategory_id())
                                .getToolcategory_name(),
                        listTools.get(i).getDescription(),
                        listTools.get(i).getParameter(),
                        listTools.get(i).getUnit()));
            }
            return listResponse;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<ToolResponse> getAllToolsWithToolCategory_id(int toolcategory_id) {
        try {
            List<Tool> listTools = toolRepository.getAllToolsWithToolCategory_id(toolcategory_id);
            List<ToolResponse> listResponse = new ArrayList<>();
            for (int i = 0; i < listTools.size(); i++) {
                listResponse.add(new ToolResponse(
                        listTools.get(i).getTool_id(),
                        listTools.get(i).getTool_name(),
                        listTools.get(i).getToolcategory_id(),
                        toolCategoryService.getToolCategory(listTools.get(i).getToolcategory_id())
                                .getToolcategory_name(),
                        listTools.get(i).getDescription(),
                        listTools.get(i).getParameter(),
                        listTools.get(i).getUnit()));
            }
            return listResponse;
        } catch (Exception e) {
            throw e;
        }
    }

    public ToolResponse getTool(int tool_id) {
        try {
            Tool tool = toolRepository.getToolByTool_id(tool_id);
            return new ToolResponse(
                    tool.getTool_id(),
                    tool.getTool_name(),
                    tool.getToolcategory_id(),
                    toolCategoryService.getToolCategory(tool.getToolcategory_id()).getToolcategory_name(),
                    tool.getDescription(),
                    tool.getParameter(),
                    tool.getUnit());
        } catch (Exception e) {
            throw e;
        }
    }

    public void createTool(ToolRequest request, String jwt) {
        try {
            Tool newTool = new Tool(
                    request.getTool_name(),
                    request.getToolcategory_id(),
                    request.getDescription(),
                    request.getParameter(),
                    request.getUnit());
            toolRepository.save(newTool);
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateTool(int tool_id, ToolRequest request, String jwt) {
        try {
            Tool updateTool = toolRepository.getToolByTool_id(tool_id);
            updateTool.setTool_name(request.getTool_name());
            updateTool.setToolcategory_id(request.getToolcategory_id());
            updateTool.setDescription(request.getDescription());
            updateTool.setParameter(request.getParameter());
            updateTool.setUnit(request.getUnit());
            toolRepository.save(updateTool);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteTool(int tool_id, String jwt) {
        try {
            toolRepository.delete(toolRepository.getToolByTool_id(tool_id));
        } catch (Exception e) {
            throw e;
        }
    }
}
