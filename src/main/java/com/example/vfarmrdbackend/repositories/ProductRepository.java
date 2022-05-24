package com.example.vfarmrdbackend.repositories;

import java.util.List;

import com.example.vfarmrdbackend.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from products p where p.product_id = :product_id", nativeQuery = true)
    Product getProductByProduct_id(@Param("product_id") int product_id);

    @Query(value = "select * from products p where lower(f.product_name) like lower(:keyword)", nativeQuery = true)
    List<Product> findProductWithKeyword(@Param("keyword") String keyword);
}
