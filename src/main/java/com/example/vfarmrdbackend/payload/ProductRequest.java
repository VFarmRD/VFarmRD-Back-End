package com.example.vfarmrdbackend.payload;

public class ProductRequest {
    private int product_id;
    private String product_name;
    private int client_id;
    private int user_id;
    private String product_inquiry;

    public ProductRequest() {
    }

    public ProductRequest(int product_id, String product_name, int client_id, int user_id, String product_inquiry) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.client_id = client_id;
        this.user_id = user_id;
        this.product_inquiry = product_inquiry;
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

}
