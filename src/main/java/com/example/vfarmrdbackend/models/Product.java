package com.example.vfarmrdbackend.models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;
    private String product_name;
    private int client_id;
    private int user_id;
    private String product_inquiry;
    private boolean project_status;
    private Date created_time;
    private Date modified_time;

    public Product() {
    }

    public Product(int product_id, String product_name, int client_id, int user_id, String product_inquiry, boolean project_status, Date created_time, Date modified_time) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.client_id = client_id;
        this.user_id = user_id;
        this.product_inquiry = product_inquiry;
        this.project_status = project_status;
        this.created_time = created_time;
        this.modified_time = modified_time;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getProduct_inquiry() {
        return product_inquiry;
    }

    public void setProduct_inquiry(String product_inquiry) {
        this.product_inquiry = product_inquiry;
    }

    public boolean isProject_status() {
        return project_status;
    }

    public void setProject_status(boolean project_status) {
        this.project_status = project_status;
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
