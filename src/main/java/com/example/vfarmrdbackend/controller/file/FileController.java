package com.example.vfarmrdbackend.controller.file;

import com.example.vfarmrdbackend.model.file.File;
import com.example.vfarmrdbackend.payload.file.FileResponse;
import com.example.vfarmrdbackend.payload.others.MessageResponse;
import com.example.vfarmrdbackend.service.file.FileService;
import com.example.vfarmrdbackend.service.others.JwtService;

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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "File", description = "The File's API")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class FileController {
        @Autowired
        FileService fileService;

        @PostMapping("/files/upload")
        @PreAuthorize("hasAuthority('staff') " +
                        "or hasAuthority('manager')")
        public ResponseEntity<?> uploadFile(@RequestParam("file") List<MultipartFile> listFile,
                        @RequestParam("object_type") String object_type,
                        @RequestParam("object_id") String object_id,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        fileService.uploadFile(listFile, object_type,
                                                        object_id, jwt));
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                        new MessageResponse("Lỗi", "Hệ thống gặp sự cố!"));
                }
        }

        @GetMapping("/files")
        @PreAuthorize("hasAuthority('staff') " +
                        "or hasAuthority('manager')")
        public ResponseEntity<?> getAllFilesWithUser_id(@RequestHeader("Authorization") String jwt) {
                try {
                        List<FileResponse> files = fileService
                                        .getAllFilesWithUser_id(JwtService.getUser_idFromToken(jwt))
                                        .map(dbFile -> {
                                                String fileDownloadUri = ServletUriComponentsBuilder
                                                                .fromCurrentContextPath()
                                                                .path("/api/files/download/")
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
                                        new MessageResponse("Lỗi", "Hệ thống gặp sự cố!"));
                }
        }

        @GetMapping("/files/{file_id}")
        public ResponseEntity<?> getFile(@PathVariable("file_id") int file_id,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        return ResponseEntity.status(HttpStatus.OK)
                                        .body(fileService.getFile(file_id, JwtService.getUser_idFromToken(jwt)));
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                        new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
                }
        }

        @GetMapping("/files/search")
        @PreAuthorize("hasAuthority('staff') " +
                        "or hasAuthority('manager')")
        public ResponseEntity<?> findFileWithKeyword(@RequestParam("keyword") String keyword,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        List<FileResponse> files = fileService
                                        .findFilesWithUser_idAndKeyword("%" + keyword + "%",
                                                        JwtService.getUser_idFromToken(jwt))
                                        .map(dbFile -> {
                                                String fileDownloadUri = ServletUriComponentsBuilder
                                                                .fromCurrentContextPath()
                                                                .path("/api/files/download/")
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
                                        new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
                }
        }

        @DeleteMapping("/files/delete/{file_id}")
        @PreAuthorize("hasAuthority('staff') " +
                        "or hasAuthority('manager')")
        public ResponseEntity<?> deleteFile(@PathVariable("file_id") int file_id,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        fileService.deleteFile(file_id, jwt);
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        "Delete File successfully!");
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                        new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
                }
        }

        @GetMapping("/files/download/{file_id}")
        public ResponseEntity<?> downloadFile(@PathVariable("file_id") int file_id,
                        @RequestHeader("Authorization") String jwt) {
                try {
                        File file = fileService.getFileDownload(file_id, jwt);
                        return ResponseEntity.ok()
                                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                                        "attachment; filename=\"" + file.getFile_name() + "\"")
                                        .body(file.getFile_data());
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                        new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));

                }
        }
}
