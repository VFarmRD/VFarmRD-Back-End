package com.example.vfarmrdbackend.payload.notification;

import java.util.List;

public class NotificationRequest {
    private List<Integer> listUser_id;
    private String message;

    public NotificationRequest() {
    }

    public List<Integer> getListUser_id() {
        return listUser_id;
    }

    public void setListUser_id(List<Integer> listUser_id) {
        this.listUser_id = listUser_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
