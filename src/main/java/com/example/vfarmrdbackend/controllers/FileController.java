package com.example.vfarmrdbackend.controllers;

import com.example.vfarmrdbackend.models.File;
import com.example.vfarmrdbackend.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class FileController {
    @Autowired
    private FileRepository repo;

    @GetMapping("/files")
    public ResponseEntity<List<File>> getAllFiles() {
        try {
            List<File> _listFiles = repo.findAll();
            if (_listFiles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_listFiles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
