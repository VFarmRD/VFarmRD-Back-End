package com.example.vfarmrdbackend.payload;

import java.util.Date;

public class NotificationGetResponse {
    private int notification_id;
    private int from_user_id;
    private String from_user_fullname;
    private int to_user_id;
    private String to_user_fullname;
    private String title;
    private String message;
    private Date created_date;

    public NotificationGetResponse() {
    }

    public int getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(int from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getFrom_user_fullname() {
        return from_user_fullname;
    }

    public void setFrom_user_fullname(String from_user_fullname) {
        this.from_user_fullname = from_user_fullname;
    }

    public int getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(int to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getTo_user_fullname() {
        return to_user_fullname;
    }

    public void setTo_user_fullname(String to_user_fullname) {
        this.to_user_fullname = to_user_fullname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

}
