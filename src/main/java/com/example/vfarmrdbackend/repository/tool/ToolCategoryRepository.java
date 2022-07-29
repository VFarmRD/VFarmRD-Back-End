package com.example.vfarmrdbackend.repository.tool;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.tool.ToolCategory;

public interface ToolCategoryRepository extends JpaRepository<ToolCategory, Integer> {

    @Query(value = "select * from toolcategories t where t.toolcategory_id = :toolcategory_id", nativeQuery = true)
    ToolCategory getToolCategoryByToolcategory_id(@Param("toolcategory_id") int toolcategory_id);
}
