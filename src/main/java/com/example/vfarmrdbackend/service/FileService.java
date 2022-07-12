package com.example.vfarmrdbackend.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.example.vfarmrdbackend.model.File;
import com.example.vfarmrdbackend.model.Log;
import com.example.vfarmrdbackend.payload.FileResponse;
import com.example.vfarmrdbackend.repository.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    LogService logService;

    public void deleteOldFile(String object_type, String object_id) {
        File oldFile = fileRepository.getFileByObjTypeAndId(object_type, String.valueOf(object_id));
        if (oldFile != null) {
            fileRepository.delete(oldFile);
        }
    }

    public Map<String, List<Integer>> store(List<MultipartFile> listFile, int user_id, String object_type,
            String object_id)
            throws IOException {
        for (int i = 0; i < listFile.size(); i++) {
            deleteOldFile(object_type, object_id);
            File newFile = new File();
            newFile.setUser_id(user_id);
            newFile.setFile_name(StringUtils.cleanPath(listFile.get(i).getOriginalFilename()));
            newFile.setFile_type(listFile.get(i).getContentType());
            newFile.setObject_type(object_type);
            newFile.setObject_id(object_id);
            newFile.setCreated_time(new Date());
            newFile.setFile_data(listFile.get(i).getBytes());
            fileRepository.save(newFile);
            logService.createLog(new Log(
                    user_id,
                    "FILE",
                    "CREATE",
                    String.valueOf(fileRepository.getNewFileUploadByUser(user_id).getFile_id()),
                    new Date()));
        }
        Map<String, List<Integer>> listFile_id = new HashMap<String, List<Integer>>();
        listFile_id.put("listFile_id", fileRepository.getNewestFile_id(object_type, object_id, listFile.size()));
        return listFile_id;
    }

    public FileResponse getFile(int file_id, int user_id) {
        File file = fileRepository.getFileByFile_id(file_id, user_id);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/files/download/")
                .path(String.valueOf(file.getFile_id()))
                .toUriString();
        return new FileResponse(
                file.getFile_name(),
                fileDownloadUri,
                file.getFile_type(),
                file.getFile_data().length);
    }

    public File getFileDownload(int file_id) {
        return fileRepository.getFileToDownload(file_id);
    }

    public Stream<File> getAllFilesWithUser_id(int user_id) {
        return fileRepository.getAllFileWithUser_id(user_id).stream();
    }

    public Stream<File> findFilesWithUser_idAndKeyword(String keyword, int user_id) {
        return fileRepository.findFileWithKeyword(keyword, user_id).stream();
    }

    public void deleteFile(int file_id, String jwt) {
        fileRepository.deleteById(file_id);
        logService.createLog(new Log(
                JwtService.getUser_idFromToken(jwt),
                "FILE",
                "DELETE",
                String.valueOf(file_id),
                new Date()));
    }
}
