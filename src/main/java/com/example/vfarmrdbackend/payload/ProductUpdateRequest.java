package com.example.vfarmrdbackend.payload;

public class ProductUpdateRequest {
    private String product_id;
    private String product_name;
    private int assigned_user_id;
    private String product_inquiry;

    public ProductUpdateRequest() {
    }

    public ProductUpdateRequest(String product_id, String product_name, int assigned_user_id, String product_inquiry) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.assigned_user_id = assigned_user_id;
        this.product_inquiry = product_inquiry;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getAssigned_user_id() {
        return assigned_user_id;
    }

    public void setAssigned_user_id(int assigned_user_id) {
        this.assigned_user_id = assigned_user_id;
    }

    public String getProduct_inquiry() {
        return product_inquiry;
    }

    public void setProduct_inquiry(String product_inquiry) {
        this.product_inquiry = product_inquiry;
    }

}
