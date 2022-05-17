package com.example.vfarmrdbackend.controllers;

import com.example.vfarmrdbackend.models.File;
import com.example.vfarmrdbackend.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/files")
public class FileController {
    @Autowired
    private FileRepository repo;

    @GetMapping("/getAllFiles")
    List<File> getAllRoles(Model model) {
        List<File> listFiles = repo.findAll();
        model.addAttribute("listFiles", listFiles);
        return listFiles;
    }

}
