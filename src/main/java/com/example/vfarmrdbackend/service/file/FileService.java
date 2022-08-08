package com.example.vfarmrdbackend.service.file;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.example.vfarmrdbackend.model.log.Log;
import com.example.vfarmrdbackend.model.error.ErrorModel;
import com.example.vfarmrdbackend.model.file.File;
import com.example.vfarmrdbackend.payload.file.FileResponse;
import com.example.vfarmrdbackend.repository.file.FileRepository;
import com.example.vfarmrdbackend.service.error.ErrorService;
import com.example.vfarmrdbackend.service.log.LogService;
import com.example.vfarmrdbackend.service.others.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    LogService logService;

    @Autowired
    ErrorService errorService;

    public void deleteOldFile(String object_type, String object_id, String jwt) {
        try {
            File oldFile = fileRepository.getFileByObjTypeAndId(object_type, String.valueOf(object_id));
            if (oldFile != null) {
                fileRepository.delete(oldFile);
            }
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "FILE DELETE OLD FILE",
                    e.getMessage(),
                    new Date()));
        }
    }

    public Map<String, List<Integer>> uploadFile(List<MultipartFile> listFile, String object_type,
            String object_id, String jwt)
            throws IOException {
        Map<String, List<Integer>> listFile_id = new HashMap<String, List<Integer>>();
        int user_id = JwtService.getUser_idFromToken(jwt);
        try {
            for (int i = 0; i < listFile.size(); i++) {
                deleteOldFile(object_type, object_id, jwt);
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
            listFile_id.put("listFile_id", fileRepository.getNewestFile_id(object_type, object_id, listFile.size()));
            return listFile_id;
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "FILE UPLOAD",
                    e.getMessage(),
                    new Date()));
            throw e;
        }

    }

    public FileResponse getFile(int file_id, int user_id) {
        try {
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
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    user_id,
                    "FILE GET",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public File getFileDownload(int file_id, String jwt) {
        try {
            return fileRepository.getFileToDownload(file_id);
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "FILE GET DOWNLOAD LINK",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public Stream<File> getAllFilesWithUser_id(int user_id) {
        try {
            return fileRepository.getAllFileWithUser_id(user_id).stream();
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    user_id,
                    "FILE GET ALL WITH USER ID",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public Stream<File> findFilesWithUser_idAndKeyword(String keyword, int user_id) {
        try {
            return fileRepository.findFileWithKeyword(keyword, user_id).stream();
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    user_id,
                    "FILE FIND",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public void deleteFile(int file_id, String jwt) {
        try {
            fileRepository.deleteById(file_id);
            logService.createLog(new Log(
                    JwtService.getUser_idFromToken(jwt),
                    "FILE",
                    "DELETE",
                    String.valueOf(file_id),
                    new Date()));
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "FILE DELETE",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public File getFileByObjTypeAndId(String object_type, String object_id, String jwt) {
        try {
            return fileRepository.getFileByObjTypeAndId(object_type, object_id);
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "FILE GET BY OBJECT TYPE AND ID",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public Stream<File> getFileByMaterial_id(String material_id, String jwt) {
        try {
            return fileRepository.getFileByMaterial_id(material_id).stream();
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "FILE GET MATERIAL",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }
}
