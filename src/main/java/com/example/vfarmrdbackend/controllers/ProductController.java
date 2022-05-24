package com.example.vfarmrdbackend.controllers;

import java.util.Date;
import java.util.List;

import com.example.vfarmrdbackend.models.Product;
import com.example.vfarmrdbackend.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class ProductController {
    @Autowired
    private ProductRepository repo;

    Date date = new Date();

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> _listProducts = repo.findAll();
            if (_listProducts.isEmpty()) {
                return new ResponseEntity<>(
                        "Can't found any product!",
                        HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_listProducts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getProductById(@PathVariable("id") int id) {
        Product _product = repo.getProductByProduct_id(id);
        if (_product != null) {
            return new ResponseEntity<>(_product, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Product not found!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> findProductWithKeyword(@RequestParam("keyword") String keyword) {
        List<Product> _listFile = repo.findProductWithKeyword("%" + keyword + "%");
        if (_listFile != null) {
            return new ResponseEntity<>(_listFile, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Product not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products/create")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            product.setCreated_time(date);
            product.setProduct_status("activated");
            repo.save(product);
            return new ResponseEntity<>(
                    "Create new product completed!",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products/update/{id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        Product _product = repo.getProductByProduct_id(id);
        if (_product != null) {
            _product.setProduct_name(product.getProduct_name());
            _product.setClient_id(product.getClient_id());
            _product.setProduct_inquiry(product.getProduct_inquiry());
            _product.setModified_time(date);
            repo.save(_product);
            return new ResponseEntity<>("Update product successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products/delete/{id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
        Product _product = repo.getProductByProduct_id(id);
        if (_product != null) {
            _product.setProduct_status("deactivated");
            _product.setModified_time(date);
            repo.save(_product);
            return new ResponseEntity<>("Delete product successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
