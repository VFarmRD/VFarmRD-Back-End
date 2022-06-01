package com.example.vfarmrdbackend.repositories;

import com.example.vfarmrdbackend.models.File;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends JpaRepository<File, Integer> {

    @Query(value = "select * from files f where f.file_id = :file_id and f.user_id = :user_id", nativeQuery = true)
    File getFileByFile_id(@Param("file_id") int file_id, @Param("user_id") int user_id);

    @Query(value = "select * from files f where f.user_id = :user_id", nativeQuery = true)
    List<File> getAllFileWithUser_id(@Param("user_id") int user_id);

    @Query(value = "select * from files f where lower(f.file_name) like lower(:keyword) and f.user_id = :user_id", nativeQuery = true)
    List<File> findFileWithKeyword(@Param("keyword") String keyword, @Param("user_id") int user_id);
}
