package com.example.vfarmrdbackend.payload;

import java.util.Date;

public class TaskCreateRequest {
    private String task_name;
    private int user_id;
    private int product_id;
    private Date estimated_date;
    private String description;

    public TaskCreateRequest() {
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public Date getEstimated_date() {
        return estimated_date;
    }

    public void setEstimated_date(Date estimated_date) {
        this.estimated_date = estimated_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
