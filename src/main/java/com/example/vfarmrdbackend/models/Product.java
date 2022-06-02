package com.example.vfarmrdbackend.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product {
    @Id
    private String product_id;
    private String product_name;
    private int client_id;
    private int created_user_id;
    private int assigned_user_id;
    private String product_inquiry;
    private String product_status;
    private Date created_time;
    private Date modified_time;

    public Product() {
    }

    public Product(String product_id, String product_name, int client_id, int created_user_id, int assigned_user_id,
            String product_inquiry, String product_status, Date created_time, Date modified_time) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.client_id = client_id;
        this.created_user_id = created_user_id;
        this.assigned_user_id = assigned_user_id;
        this.product_inquiry = product_inquiry;
        this.product_status = product_status;
        this.created_time = created_time;
        this.modified_time = modified_time;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getCreated_user_id() {
        return created_user_id;
    }

    public void setCreated_user_id(int created_user_id) {
        this.created_user_id = created_user_id;
    }

    public int getAssigned_user_id() {
        return assigned_user_id;
    }

    public void setAssigned_user_id(int assigned_user_id) {
        this.assigned_user_id = assigned_user_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getProduct_inquiry() {
        return product_inquiry;
    }

    public void setProduct_inquiry(String product_inquiry) {
        this.product_inquiry = product_inquiry;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getModified_time() {
        return modified_time;
    }

    public void setModified_time(Date modified_time) {
        this.modified_time = modified_time;
    }
}
