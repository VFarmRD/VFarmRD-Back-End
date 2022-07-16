package com.example.vfarmrdbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.Tool;

public interface ToolRepository extends JpaRepository<Tool, Integer> {
    @Query(value = "select * from tools t where t.tool_id = :tool_id", nativeQuery = true)
    Tool getToolByTool_id(@Param("tool_id") int tool_id);

    @Query(value = "select * from tools t where t.toolcategory_id = :toolcategory_id", nativeQuery = true)
    List<Tool> getAllToolsWithToolCategory_id(@Param("toolcategory_id") int toolcategory_id);

    @Query(value = "select t.tool_id from tools t", nativeQuery = true)
    List<Integer> getAllTool_id();
}
