package com.example.vfarmrdbackend.api.controller;

import com.example.vfarmrdbackend.business.model.File;
import com.example.vfarmrdbackend.business.payload.FileResponse;
import com.example.vfarmrdbackend.business.service.FileService;
import com.example.vfarmrdbackend.business.service.JwtService;
import com.example.vfarmrdbackend.data.repository.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class FileController {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    Date date = new Date();

    @PostMapping("/files/upload")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") String jwtToken) {
        String message = "";
        try {
            fileService.store(file, JwtService.getUser_idFromToken(jwtToken));
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/files")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getAllFilesWithUser_id(@RequestHeader("Authorization") String jwtToken) {
        List<FileResponse> files = fileService.getAllFilesWithUser_id(JwtService.getUser_idFromToken(jwtToken))
                .map(dbFile -> {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/api/files/")
                            .path(String.valueOf(dbFile.getFile_id()))
                            .toUriString();
                    return new FileResponse(
                            dbFile.getFile_name(),
                            fileDownloadUri,
                            dbFile.getFile_type(),
                            dbFile.getFile_data().length);
                }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable("id") int file_id,
            @RequestHeader("Authorization") String jwtToken) {
        File _file = fileRepository.getFileByFile_id(file_id, JwtService.getUser_idFromToken(jwtToken));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + _file.getFile_name() + "\"")
                .body(_file.getFile_data());
    }

    @GetMapping("/files/search")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> findFileWithKeyword(@RequestParam("keyword") String keyword,
            @RequestHeader("Authorization") String jwtToken) {
        List<FileResponse> files = fileService
                .findFilesWithUser_idAndKeyword("%" + keyword + "%", JwtService.getUser_idFromToken(jwtToken))
                .map(dbFile -> {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/api/files/")
                            .path(String.valueOf(dbFile.getFile_id()))
                            .toUriString();
                    return new FileResponse(
                            dbFile.getFile_name(),
                            fileDownloadUri,
                            dbFile.getFile_type(),
                            dbFile.getFile_data().length);
                }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);

    }

    @DeleteMapping("/files/delete/{id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> deleteFile(@PathVariable("id") int file_id) {
        try {
            fileRepository.deleteById(file_id);
            return new ResponseEntity<>("Delete File successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
