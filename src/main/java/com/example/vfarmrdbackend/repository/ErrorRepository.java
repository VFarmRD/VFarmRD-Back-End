package com.example.vfarmrdbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vfarmrdbackend.model.ErrorModel;

public interface ErrorRepository extends JpaRepository<ErrorModel, Integer> {

}
