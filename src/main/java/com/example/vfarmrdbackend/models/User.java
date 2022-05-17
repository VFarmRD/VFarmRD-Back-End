package com.example.vfarmrdbackend.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String user_name;
    private boolean user_status;
    private Date created_time;
    private Date modified_time;

    public User() {
    }

    public User(int user_id, String user_name, boolean user_status, Date created_time, Date modified_time) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_status = user_status;
        this.created_time = created_time;
        this.modified_time = modified_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public boolean isUser_status() {
        return user_status;
    }

    public void setUser_status(boolean user_status) {
        this.user_status = user_status;
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
