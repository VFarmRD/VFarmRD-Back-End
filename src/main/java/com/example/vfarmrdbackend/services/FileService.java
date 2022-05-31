package com.example.vfarmrdbackend.services;

import java.io.IOException;
import java.util.stream.Stream;

import com.example.vfarmrdbackend.models.File;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    File store(MultipartFile file,int user_id) throws IOException;

    Stream<File> getAllFilesWithUser_id(int user_id);
}
