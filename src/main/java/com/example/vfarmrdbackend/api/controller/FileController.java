package com.example.vfarmrdbackend.api.controller;

import com.example.vfarmrdbackend.business.model.File;
import com.example.vfarmrdbackend.business.payload.FileResponse;
import com.example.vfarmrdbackend.business.service.FileService;
import com.example.vfarmrdbackend.business.service.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;

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

@Tag(name = "File", description = "The File's API")
@RestController
@RequestMapping(path = "/api")
public class FileController {
        @Autowired
        private FileService fileService;

        Date date = new Date();

        @PostMapping("/files/upload")
        @PreAuthorize("hasAuthority('staff') " +
                        "or hasAuthority('manager')")
        private ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        fileService.store(file, JwtService.getUser_idFromToken(jwt));
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        "Uploaded the file successfully: " + file.getOriginalFilename());
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                                        "Could not upload the file: " + file.getOriginalFilename() + "!");
                }
        }

        @GetMapping("/files")
        @PreAuthorize("hasAuthority('staff') " +
                        "or hasAuthority('manager')")
        private ResponseEntity<?> getAllFilesWithUser_id(@RequestHeader("Authorization") String jwt) {
                try {
                        List<FileResponse> files = fileService
                                        .getAllFilesWithUser_id(JwtService.getUser_idFromToken(jwt))
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
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                        "The server is down");
                }
        }

        @GetMapping("/files/{id}")
        public ResponseEntity<?> getFile(@PathVariable("id") int file_id,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        File _file = fileService.getFile(file_id, JwtService.getUser_idFromToken(jwt));
                        return ResponseEntity.ok()
                                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                                        "attachment; filename=\"" + _file.getFile_name() + "\"")
                                        .body(_file.getFile_data());
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                        "The server is down");
                }
        }

        @GetMapping("/files/search")
        @PreAuthorize("hasAuthority('staff') " +
                        "or hasAuthority('manager')")
        private ResponseEntity<?> findFileWithKeyword(@RequestParam("keyword") String keyword,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        List<FileResponse> files = fileService
                                        .findFilesWithUser_idAndKeyword("%" + keyword + "%",
                                                        JwtService.getUser_idFromToken(jwt))
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
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                        "The server is down");
                }
        }

        @DeleteMapping("/files/delete/{id}")
        @PreAuthorize("hasAuthority('staff') " +
                        "or hasAuthority('manager')")
        private ResponseEntity<?> deleteFile(@PathVariable("id") int file_id) {
                try {
                        fileService.deleteFile(file_id);
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        "Delete File successfully!");
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                        "The server is down!");

                }
        }
}
