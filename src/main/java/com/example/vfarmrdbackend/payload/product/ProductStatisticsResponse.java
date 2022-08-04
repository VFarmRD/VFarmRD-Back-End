package com.example.vfarmrdbackend.payload.product;

public class ProductStatisticsResponse {
    private int total_product;
    private int total_product_activated;
    private int total_product_deactivated;

    public ProductStatisticsResponse() {
    }

    public ProductStatisticsResponse(int total_product, int total_product_activated, int total_product_deactivated) {
        this.total_product = total_product;
        this.total_product_activated = total_product_activated;
        this.total_product_deactivated = total_product_deactivated;
    }

    public int getTotal_product() {
        return total_product;
    }

    public void setTotal_product(int total_product) {
        this.total_product = total_product;
    }

    public int getTotal_product_activated() {
        return total_product_activated;
    }

    public void setTotal_product_activated(int total_product_activated) {
        this.total_product_activated = total_product_activated;
    }

    public int getTotal_product_deactivated() {
        return total_product_deactivated;
    }

    public void setTotal_product_deactivated(int total_product_deactivated) {
        this.total_product_deactivated = total_product_deactivated;
    }

}
