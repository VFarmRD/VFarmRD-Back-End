package com.example.vfarmrdbackend.service.product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.vfarmrdbackend.model.log.Log;
import com.example.vfarmrdbackend.model.product.Product;
import com.example.vfarmrdbackend.payload.product.request.ProductCreateRequest;
import com.example.vfarmrdbackend.payload.product.response.ProductStatisticsFromDateToDateResponse;
import com.example.vfarmrdbackend.payload.product.response.ProductStatisticsResponse;
import com.example.vfarmrdbackend.payload.product.request.ProductUpdateRequest;
import com.example.vfarmrdbackend.repository.product.ProductRepository;
import com.example.vfarmrdbackend.service.log.LogService;
import com.example.vfarmrdbackend.service.others.JwtService;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    LogService logService;

    public List<Product> getAllProducts(String product_name,
            String client_id,
            String created_user_id,
            String assigned_user_id,
            String product_status,
            int page, int size) {
        try {
            List<Product> listProducts = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Product> pageProducts = productRepository.findUserByFields("%" + product_name + "%",
                    "%" + client_id + "%", "%" + created_user_id + "%",
                    "%" + assigned_user_id + "%", "%" + product_status + "%", paging);
            listProducts = pageProducts.getContent();
            return listProducts;
        } catch (Exception e) {
            throw e;
        }
    }

    public Product getProductByProduct_id(String product_id) {
        try {
            return productRepository.getProductByProduct_id(product_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Product> getProductByFormula_id(int formula_id) {
        try {
            return productRepository.getProductByFormula_id(formula_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public String generateProductCode() {
        try {
            while (true) {
                StringBuilder sb = new StringBuilder();
                Random random = new Random();
                String product_code = "";
                int length = 13;
                for (int i = 0; i < length; i++) {
                    int random_number = random.nextInt(10);
                    sb.append(random_number);
                }
                product_code = sb.toString();
                if (productRepository.getProductByProduct_code(product_code) == null) {
                    return product_code;
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public Map<String, String> createProduct(ProductCreateRequest productCreateRequest, String jwt) {
        try {
            Product product = new Product();
            String product_code = generateProductCode();
            if (productCreateRequest.getProduct_code() != null) {
                product_code = productCreateRequest.getProduct_code();
                product.setProduct_code(productCreateRequest.getProduct_code());
            } else {
                product.setProduct_code(product_code);
            }
            product.setProduct_name(productCreateRequest.getProduct_name());
            product.setClient_id(productCreateRequest.getClient_id());
            product.setProduct_inquiry(productCreateRequest.getProduct_inquiry());
            product.setBrand_name(productCreateRequest.getBrand_name());
            product.setVolume(productCreateRequest.getVolume());
            product.setProduct_weight(productCreateRequest.getProduct_weight());
            product.setDensity(productCreateRequest.getDensity());
            product.setTolerance(productCreateRequest.getTolerance());
            product.setMaterial_norm_loss(productCreateRequest.getMaterial_norm_loss());
            product.setExpired_date(productCreateRequest.getExpired_date());
            product.setRetail_price(productCreateRequest.getRetail_price());
            product.setCreated_time(new Date());
            product.setProduct_status("activated");
            product.setUser_id(productCreateRequest.getUser_id());
            product.setFormula_id(productCreateRequest.getFormula_id());
            productRepository.save(product);
            Map<String, String> map = new HashMap<>();
            map.put("object_type", "products");
            map.put("object_id",
                    String.valueOf(productRepository.getProduct_idByProduct_code(product_code)));
            map.put("product_code", product_code);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "PRODUCT",
                    "CREATE",
                    String.valueOf(productRepository.getProduct_idByProduct_code(product_code)),
                    new Date()));
            return map;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean updateProduct(ProductUpdateRequest productUpdateRequest, String jwt) {
        try {
            Product product = productRepository.getProductByProduct_id(productUpdateRequest.getProduct_id());
            if (product != null) {
                product.setProduct_name(productUpdateRequest.getProduct_name());
                if (productUpdateRequest.getProduct_code() != null) {
                    product.setProduct_code(productUpdateRequest.getProduct_code());
                }
                product.setProduct_inquiry(productUpdateRequest.getProduct_inquiry());
                product.setModified_time(new Date());
                product.setExpired_date(productUpdateRequest.getExpired_date());
                product.setRetail_price(productUpdateRequest.getRetail_price());
                productRepository.save(product);
                logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                        "PRODUCT",
                        "UPDATE",
                        String.valueOf(productUpdateRequest.getProduct_id()),
                        new Date()));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean deleteProduct(String product_id, String jwt) {
        try {
            Product product = productRepository.getProductByProduct_id(product_id);
            if (product != null) {
                product.setProduct_status("deactivated");
                product.setModified_time(new Date());
                productRepository.save(product);
                logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                        "PRODUCT",
                        "DELETE",
                        product_id,
                        new Date()));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkProduct_codeExisted(String product_code) {
        try {
            Product product = productRepository.getProductByProduct_code(product_code);
            if (product != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public ProductStatisticsResponse getProductStatisticsOfAllTime(String jwt) {
        try {
            return new ProductStatisticsResponse(productRepository.getTotalProduct(),
                    productRepository.getTotalProductActivated(),
                    productRepository.getTotalProductDeactivated());
        } catch (Exception e) {
            throw e;
        }
    }

    public List<ProductStatisticsFromDateToDateResponse> getProductStatisticsFromDateToDate(String jwt,
            String from_date, String to_date) {
        try {
            LocalDate start = LocalDate.parse(from_date.split(" ")[0]);
            LocalDate end = LocalDate.parse(to_date.split(" ")[0]);
            List<LocalDate> totalDates = new ArrayList<>();
            while (!start.isAfter(end)) {
                totalDates.add(start);
                start = start.plusDays(1);
            }
            List<ProductStatisticsFromDateToDateResponse> listResponses = new ArrayList<>();
            for (int i = 0; i < listResponses.size(); i++) {
                if (productRepository.getTotalProductFromDateToDate(totalDates.get(i) + " 00:00:00",
                        totalDates.get(i) + " 23:59:59") != 0) {
                    listResponses.add(new ProductStatisticsFromDateToDateResponse(listResponses.get(i).toString(),
                            productRepository.getTotalProductFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59"),
                            productRepository.getTotalProductActivatedFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59"),
                            productRepository.getTotalProductDeactivatedFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59")));
                }
            }
            return listResponses;
        } catch (Exception e) {
            throw e;
        }
    }

    public ProductStatisticsResponse getProductStatisticsWithMonthAndYear(String jwt, int month, int year) {
        try {
            return new ProductStatisticsResponse(productRepository.getTotalProductWithMonthAndYear(month, year),
                    productRepository.getTotalProductActivatedWithMonthAndYear(month, year),
                    productRepository.getTotalProductDeactivatedWithMonthAndYear(month, year));
        } catch (Exception e) {
            throw e;
        }
    }
}
