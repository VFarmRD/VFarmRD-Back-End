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
import com.example.vfarmrdbackend.payload.ProductCreateRequest;
import com.example.vfarmrdbackend.payload.ProductUpdateRequest;
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
        List<Product> listProducts = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Product> pageProducts = productRepository.findUserByFields("%" + product_name + "%",
                "%" + client_id + "%", "%" + created_user_id + "%",
                "%" + assigned_user_id + "%", "%" + product_status + "%", paging);
        listProducts = pageProducts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("products", listProducts);
        response.put("currentPage", pageProducts.getNumber());
        response.put("totalItems", pageProducts.getTotalElements());
        response.put("totalPages", pageProducts.getTotalPages());
        return listProducts;
    }

    public Product getProductByProduct_id(String product_id) {
        return productRepository.getProductByProduct_id(product_id);
    }

    public void createProduct(ProductCreateRequest productCreateRequest, String jwt) {
        date = new Date();
        Product product = new Product();
        product.setProduct_name(productCreateRequest.getProduct_name());
        product.setClient_id(productCreateRequest.getClient_id());
        product.setAssigned_user_id(productCreateRequest.getAssigned_user_id());
        product.setCreated_user_id(JwtService.getUser_idFromToken(jwt));
        product.setProduct_inquiry(productCreateRequest.getProduct_inquiry());
        product.setCreated_time(date);
        product.setProduct_status("activated");
        productRepository.save(product);
    }

    public boolean updateProduct(ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.getProductByProduct_id(productUpdateRequest.getProduct_id());
        if (product != null) {
            date = new Date();
            product.setProduct_name(productUpdateRequest.getProduct_name());
            product.setAssigned_user_id(productUpdateRequest.getAssigned_user_id());
            product.setProduct_inquiry(productUpdateRequest.getProduct_inquiry());
            product.setModified_time(date);
            productRepository.save(product);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteProduct(String product_id) {
        Product product = productRepository.getProductByProduct_id(product_id);
        if (product != null) {
            product.setProduct_status("deactivated");
            product.setModified_time(date);
            productRepository.save(product);
            return true;
        } else {
            return false;
        }
    }
}
