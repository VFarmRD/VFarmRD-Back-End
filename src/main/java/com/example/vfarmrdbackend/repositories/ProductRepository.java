package com.example.vfarmrdbackend.repositories;

import com.example.vfarmrdbackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
