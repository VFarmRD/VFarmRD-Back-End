package com.example.vfarmrdbackend.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import com.example.vfarmrdbackend.model.File;
import com.example.vfarmrdbackend.repository.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    Date date;

    public List<Integer> store(List<MultipartFile> listFile, int user_id, String object_type, String object_id)
            throws IOException {
        for (int i = 0; i < listFile.size(); i++) {
            date = new Date();
            File newFile = new File();
            newFile.setUser_id(user_id);
            newFile.setFile_name(StringUtils.cleanPath(listFile.get(i).getOriginalFilename()));
            newFile.setFile_type(listFile.get(i).getContentType());
            newFile.setObject_type(object_type);
            newFile.setObject_id(object_id);
            newFile.setCreated_time(date);
            newFile.setFile_data(listFile.get(i).getBytes());
            fileRepository.save(newFile);
        }
        return fileRepository.getNewestFile_id(object_type, object_id, listFile.size());
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

    public void deleteFile(int file_id) {
        fileRepository.deleteById(file_id);
    }

    // public int getNewestFile_id(String object_type, String object_id) {
    // return fileRepository.getNewestFile_id(object_type, object_id);
    // }
}
