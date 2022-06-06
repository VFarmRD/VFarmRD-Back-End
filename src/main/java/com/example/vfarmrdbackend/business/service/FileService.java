package com.example.vfarmrdbackend.business.service;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Stream;

import com.example.vfarmrdbackend.business.model.File;
import com.example.vfarmrdbackend.data.repository.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    Date date = new Date();

    public File store(MultipartFile file, int user_id) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File image = new File();
        image.setUser_id(user_id);
        image.setFile_name(fileName);
        image.setFile_type(file.getContentType());
        image.setCreated_time(date);
        image.setFile_data(file.getBytes());
        return fileRepository.save(image);
    }

    public File getFile(int file_id, int user_id) {
        return fileRepository.getFileByFile_id(file_id, user_id);
    }

    public Stream<File> getAllFilesWithUser_id(int user_id) {
        return fileRepository.getAllFileWithUser_id(user_id).stream();
    }

    public Stream<File> findFilesWithUser_idAndKeyword(String keyword, int user_id) {
        return fileRepository.findFileWithKeyword(keyword, user_id).stream();
    }

}
