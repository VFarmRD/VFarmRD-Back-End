package com.example.vfarmrdbackend.repository.error;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vfarmrdbackend.model.error.ErrorModel;

public interface ErrorRepository extends JpaRepository<ErrorModel, Integer> {

}
