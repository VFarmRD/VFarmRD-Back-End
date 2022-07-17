package com.example.vfarmrdbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.ErrorModel;
import com.example.vfarmrdbackend.repository.ErrorRepository;

@Service
public class ErrorService {

    @Autowired
    ErrorRepository errorRepository;

    public void createError(ErrorModel error) {
        errorRepository.save(error);
    }
}
