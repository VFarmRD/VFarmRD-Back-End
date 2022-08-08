package com.example.vfarmrdbackend.repository.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

        @Query(value = "select * from products p where p.product_id = :product_id", nativeQuery = true)
        Product getProductByProduct_id(@Param("product_id") String product_id);

        @Query(value = "select * from products p where lower(p.product_name) like lower(:keyword)", nativeQuery = true)
        List<Product> findProductWithKeyword(@Param("keyword") String keyword);

        @Query(value = "select * from products p " +
                        "where lower(p.product_name) like lower(:product_name) and " +
                        "p.client_id like :client_id and " +
                        "p.created_user_id like :created_user_id and " +
                        "p.assigned_user_id like :assigned_user_id and " +
                        "p.product_status like :product_status ", nativeQuery = true)
        Page<Product> findUserByFields(@Param("product_name") String product_name,
                        @Param("client_id") String client_id,
                        @Param("created_user_id") String created_user_id,
                        @Param("assigned_user_id") String assigned_user_id,
                        @Param("product_status") String product_status,
                        Pageable pageable);

        @Query(value = "select * from products", nativeQuery = true)
        Page<Product> findAllProduct(Pageable pageable);

        @Query(value = "select p.product_id from products p where p.product_code = :product_code", nativeQuery = true)
        int getProduct_idByProduct_code(@Param("product_code") String product_code);

        @Query(value = "select * from products p where p.product_code = :product_code", nativeQuery = true)
        Product getProductByProduct_code(@Param("product_code") String product_code);

        @Query(value = "select * from products p where p.formula_id = :formula_id", nativeQuery = true)
        List<Product> getProductByFormula_id(@Param("formula_id") int formula_id);

        @Query(value = "SELECT COUNT(*) FROM products p;", nativeQuery = true)
        int getTotalProduct();

        @Query(value = "SELECT COUNT(*) FROM products p where p.created_time between :from_date and :to_date;", nativeQuery = true)
        int getTotalProductFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM products p where month(p.created_time) = :month and year(p.created_time) = :year ;", nativeQuery = true)
        int getTotalProductWithMonthAndYear(@Param("month") int month, @Param("year") int year);

        @Query(value = "SELECT COUNT(*) FROM products p where p.product_status = 'activated';", nativeQuery = true)
        int getTotalProductActivated();

        @Query(value = "SELECT COUNT(*) FROM products p where (p.created_time between :from_date and :to_date) and p.product_status = 'activated';", nativeQuery = true)
        int getTotalProductActivatedFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM products p where month(p.created_time) = :month and year(p.created_time) = :year and p.product_status = 'activated';", nativeQuery = true)
        int getTotalProductActivatedWithMonthAndYear(@Param("month") int month, @Param("year") int year);

        @Query(value = "SELECT COUNT(*) FROM products p where p.product_status = 'deactivated';", nativeQuery = true)
        int getTotalProductDeactivated();

        @Query(value = "SELECT COUNT(*) FROM products p where (p.created_time between :from_date and :to_date) and p.product_status = 'deactivated';", nativeQuery = true)
        int getTotalProductDeactivatedFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM products p where month(p.created_time) = :month and year(p.created_time) = :year and p.product_status = 'deactivated';", nativeQuery = true)
        int getTotalProductDeactivatedWithMonthAndYear(@Param("month") int month, @Param("year") int year);
}
