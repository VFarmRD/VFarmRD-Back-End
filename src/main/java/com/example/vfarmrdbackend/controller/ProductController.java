package com.example.vfarmrdbackend.controller;

import java.util.List;

import com.example.vfarmrdbackend.model.Product;
import com.example.vfarmrdbackend.payload.ProductRequest;
import com.example.vfarmrdbackend.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product", description = "The Product's API")
@RestController
@RequestMapping(path = "/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "", required = false) String product_name,
            @RequestParam(defaultValue = "", required = false) String client_id,
            @RequestParam(defaultValue = "", required = false) String created_user_id,
            @RequestParam(defaultValue = "", required = false) String assigned_user_id,
            @RequestParam(defaultValue = "activated", required = false) String product_status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size) {
        try {
            List<Product> _listProducts = productService.getAllProducts(product_name,
                    client_id, created_user_id, assigned_user_id,
                    product_status, page, size);
            return ResponseEntity.status(HttpStatus.OK).body(_listProducts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @GetMapping("/products/{product_id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getProductByProduct_id(@PathVariable("product_id") String product_id) {
        Product _product = productService.getProductByProduct_id(product_id);
        if (_product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(_product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Product not found!");
        }
    }

    @PostMapping("/products/create")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest,
            @RequestHeader("Authorization") String jwt) {
        try {
            productService.createProduct(productRequest, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Create new product completed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PutMapping("/products/update")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest) {
        try {
            productService.updateProduct(productRequest);
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Update product successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }

    @PutMapping("/products/delete/{product_id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> deleteProduct(@PathVariable("product_id") String product_id) {
        try {
            productService.deleteProduct(product_id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Delete product successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "The server is down!");
        }
    }
}