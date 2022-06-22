package com.example.vfarmrdbackend.payload;

public class ProductCreateRequest {
    private String product_name;
    private String client_id;
    private int assigned_user_id;
    private String product_inquiry;
    private String brand_name;
    private int volume;
    private int capacity;
    private float d;
    private float tolerance;
    private int material_norm_loss;

    public ProductCreateRequest() {
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }

    public float getTolerance() {
        return tolerance;
    }

    public void setTolerance(float tolerance) {
        this.tolerance = tolerance;
    }

    public int getMaterial_norm_loss() {
        return material_norm_loss;
    }

    public void setMaterial_norm_loss(int material_norm_loss) {
        this.material_norm_loss = material_norm_loss;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
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
