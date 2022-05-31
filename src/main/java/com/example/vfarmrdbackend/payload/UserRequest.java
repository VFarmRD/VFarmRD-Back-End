package com.example.vfarmrdbackend.payload;

public class UserRequest {
    private int user_id;
    private String email;
    private String fullname;
    private String phone;
    private String password;

    public UserRequest() {
    }

    public UserRequest(int user_id, String email, String fullname, String phone, String password) {
        this.user_id = user_id;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
