package com.example.vfarmrdbackend.repositories;

import com.example.vfarmrdbackend.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Integer> {
}
