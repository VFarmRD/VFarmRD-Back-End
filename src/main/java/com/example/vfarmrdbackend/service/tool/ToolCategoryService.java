package com.example.vfarmrdbackend.service.tool;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.tool.ToolCategory;
import com.example.vfarmrdbackend.payload.tool.ToolCategoryRequest;
import com.example.vfarmrdbackend.repository.tool.ToolCategoryRepository;

@Service
public class ToolCategoryService {
    @Autowired
    ToolCategoryRepository toolCategoryRepository;

    public List<ToolCategory> getAllToolCategories() {
        return toolCategoryRepository.findAll();
    }

    public ToolCategory getToolCategory(int toolcategory_id) {
        return toolCategoryRepository.getToolCategoryByToolcategory_id(toolcategory_id);
    }

    public void createToolCategory(ToolCategoryRequest request, String jwt) {
        toolCategoryRepository.save(new ToolCategory(request.getToolcategory_name(), request.getDescription()));
    }

    public void updateToolCategory(int toolcategory_id, ToolCategoryRequest request, String jwt) {
        ToolCategory updateToolCategory = toolCategoryRepository.getToolCategoryByToolcategory_id(toolcategory_id);
        updateToolCategory.setToolcategory_name(request.getToolcategory_name());
        updateToolCategory.setDescription(request.getDescription());
        toolCategoryRepository.save(updateToolCategory);
    }

    public void deleteToolCategory(int toolcategory_id, String jwt) {
        toolCategoryRepository.delete(toolCategoryRepository.getToolCategoryByToolcategory_id(toolcategory_id));
    }
}
