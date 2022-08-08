package com.example.vfarmrdbackend.repository.file;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.file.File;

public interface FileRepository extends JpaRepository<File, Integer> {

    @Query(value = "select * from files f where f.file_id = :file_id and f.user_id = :user_id", nativeQuery = true)
    File getFileByFile_id(@Param("file_id") int file_id, @Param("user_id") int user_id);

    @Query(value = "select * from files f where f.file_id = :file_id", nativeQuery = true)
    File getFileToDownload(@Param("file_id") int file_id);

    @Query(value = "select * from files f where f.user_id = :user_id", nativeQuery = true)
    List<File> getAllFileWithUser_id(@Param("user_id") int user_id);

    @Query(value = "select * from files f where lower(f.file_name) like lower(:keyword) and f.user_id = :user_id", nativeQuery = true)
    List<File> findFileWithKeyword(@Param("keyword") String keyword, @Param("user_id") int user_id);

    @Query(value = "select * from files f where f.object_type = :object_type and f.object_id = :object_id order by f.file_id desc limit :limit_size", nativeQuery = true)
    List<Integer> getNewestFile_id(@Param("object_type") String object_type, @Param("object_id") String object_id,
            @Param("limit_size") int limit_size);

    @Query(value = "select * from files f where f.object_type = :object_type and f.object_id = :object_id", nativeQuery = true)
    File getFileByObjTypeAndId(@Param("object_type") String object_type, @Param("object_id") String object_id);

    @Query(value = "select * from files f where f.user_id = :user_id order by f.file_id desc limit 1", nativeQuery = true)
    File getNewFileUploadByUser(@Param("user_id") int user_id);

    @Query(value = "select * from files f where lower(f.file_name) like lower(:material_id)", nativeQuery = true)
    List<File> getFileByMaterial_id(@Param("material_id") String material_id);
}
