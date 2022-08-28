package com.example.vfarmrdbackend.payload.user;

import java.util.Date;

public class UserInProjectResponse {
    private int user_id;
    private String fullname;
    private Date assigned_date;

    public UserInProjectResponse() {
    }

    public UserInProjectResponse(int user_id, String fullname, Date assigned_date) {
        this.user_id = user_id;
        this.fullname = fullname;
        this.assigned_date = assigned_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getAssigned_date() {
        return assigned_date;
    }

    public void setAssigned_date(Date assigned_date) {
        this.assigned_date = assigned_date;
    }

}
