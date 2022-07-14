package com.example.vfarmrdbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.Tool;

public interface ToolRepository extends JpaRepository<Tool, Integer> {
    @Query(value = "select * from tools t where t.tool_id = :tool_id", nativeQuery = true)
    Tool getToolByTool_id(@Param("tool_id") int tool_id);
}
