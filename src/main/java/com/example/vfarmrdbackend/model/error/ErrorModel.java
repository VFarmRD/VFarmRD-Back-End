package com.example.vfarmrdbackend.model.error;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "errors")
public class ErrorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int error_id;
    private int user_id;
    private String object_name;
    private String error_name;
    private Date created_time;

    public ErrorModel() {
    }

    public ErrorModel(int user_id, String object_name, String error_name, Date created_time) {
        this.user_id = user_id;
        this.object_name = object_name;
        this.error_name = error_name;
        this.created_time = created_time;
    }

    public int getError_id() {
        return error_id;
    }

    public void setError_id(int error_id) {
        this.error_id = error_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public String getError_name() {
        return error_name;
    }

    public void setError_name(String error_name) {
        this.error_name = error_name;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

}
