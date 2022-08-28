package com.example.vfarmrdbackend.controller.product;

import java.util.List;

import com.example.vfarmrdbackend.model.product.Product;
import com.example.vfarmrdbackend.payload.product.request.ProductCreateRequest;
import com.example.vfarmrdbackend.payload.product.request.ProductUpdateRequest;
import com.example.vfarmrdbackend.payload.others.response.MessageResponse;
import com.example.vfarmrdbackend.service.others.ValidatorService;
import com.example.vfarmrdbackend.service.product.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ValidatorService validatorService;

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
            List<Product> listProducts = productService.getAllProducts(product_name,
                    client_id, created_user_id, assigned_user_id,
                    product_status, page, size);
            return ResponseEntity.status(HttpStatus.OK).body(listProducts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/products/{product_id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getProductByProduct_id(@PathVariable("product_id") String product_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getProductByProduct_id(product_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/products/formulas/{formula_id}")
    @PreAuthorize("hasAuthority('staff') " +
            "or hasAuthority('manager')")
    public ResponseEntity<?> getProductByFormula_id(@PathVariable("formula_id") int formula_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getProductByFormula_id(formula_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PostMapping("/products/create")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateRequest productCreateRequest,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (validatorService.validateString(productCreateRequest.getProduct_name())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Tên sản phẩm không hợp lệ!"));
            }
            if (validatorService.validateString(productCreateRequest.getBrand_name())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Tên doanh nghiệp không hợp lệ!"));
            }
            if (validatorService.validateFloat(productCreateRequest.getVolume())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Thể tích sản phẩm không hợp lệ!"));
            }
            if (validatorService.validateFloat(productCreateRequest.getProduct_weight())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Khối lượng sản phẩm không hợp lệ!"));
            }
            if (validatorService.validateFloat(productCreateRequest.getDensity())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Khối lượng riêng không hợp lệ!"));
            }
            if (validatorService.validateFloat(productCreateRequest.getTolerance())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Dung sai không hợp lệ!"));
            }
            if (validatorService.validateInteger(productCreateRequest.getMaterial_norm_loss())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Độ hao hụt nguyên liệu không hợp lệ!"));
            }
            if (validatorService.validateFloat(productCreateRequest.getRetail_price())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Giá bán không hợp lệ!"));
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    productService.createProduct(productCreateRequest, jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/products/update")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateRequest productUpdateRequest,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (validatorService.validateString(productUpdateRequest.getProduct_name())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Tên sản phẩm không hợp lệ!"));
            }
            if (validatorService.validateString(productUpdateRequest.getBrand_name())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Tên doanh nghiệp không hợp lệ!"));
            }
            if (validatorService.validateFloat(productUpdateRequest.getVolume())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Thể tích sản phẩm không hợp lệ!"));
            }
            if (validatorService.validateFloat(productUpdateRequest.getProduct_weight())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Khối lượng sản phẩm không hợp lệ!"));
            }
            if (validatorService.validateFloat(productUpdateRequest.getDensity())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Khối lượng riêng không hợp lệ!"));
            }
            if (validatorService.validateFloat(productUpdateRequest.getTolerance())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Dung sai không hợp lệ!"));
            }
            if (validatorService.validateInteger(productUpdateRequest.getMaterial_norm_loss())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Độ hao hụt nguyên liệu không hợp lệ!"));
            }
            if (validatorService.validateFloat(productUpdateRequest.getRetail_price())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new MessageResponse("Lỗi", "Giá bán không hợp lệ!"));
            }
            productService.updateProduct(productUpdateRequest, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Cập nhật sản phẩm thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @PutMapping("/products/delete/{product_id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> deleteProduct(@PathVariable("product_id") String product_id,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            productService.deleteProduct(product_id, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new MessageResponse("Thành công", "Xóa sản phẩm thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @DeleteMapping("/products/{product_code}/remove-from-system")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> removeProductFromSystem(@PathVariable("product_code") String product_code,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            productService.deleteProductFromSystem(product_code, jwt);
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Delete product successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }

    @GetMapping("/products/statistics")
    @PreAuthorize("hasAuthority('manager') or hasAuthority('staff')")
    public ResponseEntity<?> getProductStatistics(
            @RequestParam(defaultValue = "none", required = false) String from_date,
            @RequestParam(defaultValue = "none", required = false) String to_date,
            @RequestParam(defaultValue = "0", required = false) int month,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestHeader(required = false, value = "Authorization") String jwt) {
        try {
            if (!from_date.equals("none") && !to_date.equals("none")) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        productService.getProductStatisticsFromDateToDate(jwt, from_date, to_date));
            } else if (month != 0 && year != 0) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        productService.getProductStatisticsWithMonthAndYear(jwt, month, year));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(
                        productService.getProductStatisticsOfAllTime(jwt));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Lỗi", "Hệ thống đã gặp sự cố!"));
        }
    }
}
