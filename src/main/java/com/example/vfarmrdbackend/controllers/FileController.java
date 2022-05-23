package com.example.vfarmrdbackend.controllers;

import com.example.vfarmrdbackend.models.File;
import com.example.vfarmrdbackend.models.User;
import com.example.vfarmrdbackend.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class FileController {
    @Autowired
    private FileRepository repo;

    Date date = new Date();

    // @GetMapping("/files")
    // public ResponseEntity<?> getAllFiles() {
    // try {
    // List<File> _listFiles = repo.findAll();
    // if (_listFiles.isEmpty()) {
    // return new ResponseEntity<>(
    // "Can't found any file!",
    // HttpStatus.NO_CONTENT);
    // }
    // return new ResponseEntity<>(_listFiles, HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(
    // "The server is down!",
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    @GetMapping("/files")
    @PreAuthorize("hasAuthority('admin')" +
            "or hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getAllFilesWithUser_id(@RequestBody User user) {
        try {
            List<File> _listFiles = repo.getAllFileWithUser_id(user.getUser_id());
            if (_listFiles.isEmpty()) {
                return new ResponseEntity<>(
                        "Can't found any file!",
                        HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_listFiles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/files/{id}")
    @PreAuthorize("hasAuthority('admin')" +
            "or hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getFileById(@PathVariable("id") int id) {
        File _file = repo.findFileByFile_id(id);
        if (_file != null) {
            return new ResponseEntity<>(_file, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("File not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/files")
    @PreAuthorize("hasAuthority('admin')" +
            "or hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> createFile(@RequestBody File file) {
        try {
            file.setCreated_time(date);
            repo.save(file);
            return new ResponseEntity<>(
                    "Create new file completed!",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/files/{id}")
    @PreAuthorize("hasAuthority('admin')" +
            "or hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> updateRole(@PathVariable("id") int id, @RequestBody File file) {
        File _file = repo.findFileByFile_id(id);
        if (_file != null) {
            _file.setFile_name(file.getFile_name());
            _file.setFile_path(file.getFile_path());
            _file.setFile_type(file.getFile_type());
            _file.setModified_time(date);
            _file.setFile_data(file.isFile_data());
            repo.save(_file);
            return new ResponseEntity<>("Update file successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/files/{id}")
    @PreAuthorize("hasAuthority('admin')" +
            "or hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> deleteFile(@PathVariable("id") int id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>("Delete File successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
