package com.example.vfarmrdbackend.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.vfarmrdbackend.models.Product;
import com.example.vfarmrdbackend.payload.ProductRequest;
import com.example.vfarmrdbackend.repositories.ProductRepository;
import com.example.vfarmrdbackend.services.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping(path = "/api")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    Date date;

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "", required = false) String product_name,
            @RequestParam(defaultValue = "", required = false) String client_id,
            @RequestParam(defaultValue = "", required = false) String created_user_id,
            @RequestParam(defaultValue = "", required = false) String assigned_user_id,
            @RequestParam(defaultValue = "", required = false) String product_status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size) {
        try {
            List<Product> _listProducts = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Product> pageProducts;
            if (product_name != null || client_id != null || created_user_id != null ||
                    assigned_user_id != null || product_status != null) {
                pageProducts = productRepository.findUserByFields("%" + product_name + "%",
                        "%" + client_id + "%", "%" + created_user_id + "%",
                        "%" + assigned_user_id + "%", "%" + product_status + "%", paging);
            } else {
                pageProducts = productRepository.findAllProduct(paging);
            }
            _listProducts = pageProducts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("products", _listProducts);
            response.put("currentPage", pageProducts.getNumber());
            response.put("totalItems", pageProducts.getTotalElements());
            response.put("totalPages", pageProducts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
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
        Product _product = productRepository.getProductByProduct_id(id);
        if (_product != null) {
            return new ResponseEntity<>(_product, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Product not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products/create")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest,
            @RequestHeader("Authorization") String jwtToken) {
        try {
            date = new Date();
            Product _product = new Product();
            _product.setProduct_name(productRequest.getProduct_name());
            _product.setClient_id(productRequest.getClient_id());
            _product.setAssigned_user_id(productRequest.getAssigned_user_id());
            _product.setCreated_user_id(JwtService.getUser_idFromToken(jwtToken));
            _product.setProduct_inquiry(productRequest.getProduct_inquiry());
            _product.setCreated_time(date);
            _product.setProduct_status("activated");
            productRepository.save(_product);
            return new ResponseEntity<>(
                    "Create new product completed!",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "The server is down!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products/update")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest) {
        Product _product = productRepository.getProductByProduct_id(productRequest.getProduct_id());
        if (_product != null) {
            date = new Date();
            _product.setProduct_name(productRequest.getProduct_name());
            _product.setClient_id(productRequest.getClient_id());
            _product.setAssigned_user_id(productRequest.getAssigned_user_id());
            _product.setProduct_inquiry(productRequest.getProduct_inquiry());
            _product.setModified_time(date);
            productRepository.save(_product);
            return new ResponseEntity<>("Update product successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products/delete/{id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
        Product _product = productRepository.getProductByProduct_id(id);
        if (_product != null) {
            _product.setProduct_status("deactivated");
            _product.setModified_time(date);
            productRepository.save(_product);
            return new ResponseEntity<>("Delete product successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
