package com.example.vfarmrdbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Product;
import com.example.vfarmrdbackend.payload.ProductRequest;
import com.example.vfarmrdbackend.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    Date date;

    // Map<String, Object>
    public List<Product> getAllProducts(String product_name,
            String client_id,
            String created_user_id,
            String assigned_user_id,
            String product_status,
            int page, int size) {
        List<Product> _listProducts = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Product> _pageProducts = productRepository.findUserByFields("%" + product_name + "%",
                "%" + client_id + "%", "%" + created_user_id + "%",
                "%" + assigned_user_id + "%", "%" + product_status + "%", paging);
        _listProducts = _pageProducts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("products", _listProducts);
        response.put("currentPage", _pageProducts.getNumber());
        response.put("totalItems", _pageProducts.getTotalElements());
        response.put("totalPages", _pageProducts.getTotalPages());
        return _listProducts;
    }

    public Product getProductByProduct_id(String product_id) {
        return productRepository.getProductByProduct_id(product_id);
    }

    public void createProduct(ProductRequest productRequest, String jwt) {
        date = new Date();
        Product _product = new Product();
        _product.setProduct_id(productRequest.getProduct_id());
        _product.setProduct_name(productRequest.getProduct_name());
        _product.setClient_id(productRequest.getClient_id());
        _product.setAssigned_user_id(productRequest.getAssigned_user_id());
        _product.setCreated_user_id(JwtService.getUser_idFromToken(jwt));
        _product.setProduct_inquiry(productRequest.getProduct_inquiry());
        _product.setCreated_time(date);
        _product.setProduct_status("activated");
        productRepository.save(_product);
    }

    public boolean updateProduct(ProductRequest productRequest) {
        Product _product = productRepository.getProductByProduct_id(productRequest.getProduct_id());
        if (_product != null) {
            date = new Date();
            _product.setProduct_name(productRequest.getProduct_name());
            _product.setClient_id(productRequest.getClient_id());
            _product.setAssigned_user_id(productRequest.getAssigned_user_id());
            _product.setProduct_inquiry(productRequest.getProduct_inquiry());
            _product.setModified_time(date);
            productRepository.save(_product);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteProduct(String product_id) {
        Product _product = productRepository.getProductByProduct_id(product_id);
        if (_product != null) {
            _product.setProduct_status("deactivated");
            _product.setModified_time(date);
            productRepository.save(_product);
            return true;
        } else {
            return false;
        }
    }
}
